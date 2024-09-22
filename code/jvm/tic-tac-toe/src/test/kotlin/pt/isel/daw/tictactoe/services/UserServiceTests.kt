package pt.isel.daw.tictactoe.services

import org.jdbi.v3.core.Jdbi
import org.junit.jupiter.api.Test
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pt.isel.daw.tictactoe.TestClock
import pt.isel.daw.tictactoe.domain.Sha256TokenEncoder
import pt.isel.daw.tictactoe.domain.UsersDomain
import pt.isel.daw.tictactoe.domain.UsersDomainConfig
import pt.isel.daw.tictactoe.repository.jdbi.JdbiTransactionManager
import pt.isel.daw.tictactoe.repository.jdbi.configureWithAppRequirements
import pt.isel.daw.tictactoe.utils.Either
import java.util.Base64
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class UserServiceTests {
    @Test
    fun `can create user, token, and retrieve by token`() {
        // given: a user service
        val testClock = TestClock()
        val userService = createUsersService(testClock)

        // when: creating a user
        val username = newTestUserName()
        val password = "changeit"
        val createUserResult = userService.createUser(username, password)

        // then: the creation is successful
        when (createUserResult) {
            is Either.Left -> fail("Unexpected $createUserResult")
            is Either.Right -> assertTrue(createUserResult.value > 0)
        }

        // when: creating a token
        val createTokenResult = userService.createToken(username, password)

        // then: the creation is successful
        val token = when (createTokenResult) {
            is Either.Left -> fail(createTokenResult.toString())
            is Either.Right -> createTokenResult.value.tokenValue
        }

        // and: the token bytes have the expected length
        val tokenBytes = Base64.getUrlDecoder().decode(token)
        assertEquals(256 / 8, tokenBytes.size)

        // when: retrieving the user by token
        val user = userService.getUserByToken(token)

        // then: a user is found
        assertNotNull(user)

        // and: has the expected name
        assertEquals(username, user.username)
    }

    @Test
    fun `can use token during rolling period but not after absolute TTL`() {
        // given: a user service
        val testClock = TestClock()
        val tokenTtl = 90.minutes
        val tokenRollingTtl = 30.minutes
        val userService = createUsersService(testClock, tokenTtl, tokenRollingTtl)

        // when: creating a user
        val username = newTestUserName()
        val password = "changeit"
        val createUserResult = userService.createUser(username, password)

        // then: the creation is successful
        when (createUserResult) {
            is Either.Left -> fail("Unexpected $createUserResult")
            is Either.Right -> assertTrue(createUserResult.value > 0)
        }

        // when: creating a token
        val createTokenResult = userService.createToken(username, password)

        // then: the creation is successful
        val token = when (createTokenResult) {
            is Either.Left -> fail(createTokenResult.toString())
            is Either.Right -> createTokenResult.value.tokenValue
        }

        // when: retrieving the user after (rolling TTL - 1s) intervals
        val startInstant = testClock.now()
        while (true) {
            testClock.advance(tokenRollingTtl.minus(1.seconds))
            userService.getUserByToken(token) ?: break
        }

        // then: user is not found only after the absolute TTL has elapsed
        assertTrue((testClock.now() - startInstant) > tokenTtl)
    }

    @Test
    fun `can limit the number of tokens`() {
        // given: a user service
        val testClock = TestClock()
        val maxTokensPerUser = 5
        val userService = createUsersService(testClock, maxTokensPerUser = maxTokensPerUser)

        // when: creating a user
        val username = newTestUserName()
        val password = "changeit"
        val createUserResult = userService.createUser(username, password)

        // then: the creation is successful
        when (createUserResult) {
            is Either.Left -> fail("Unexpected $createUserResult")
            is Either.Right -> assertTrue(createUserResult.value > 0)
        }

        // when: creating MAX tokens
        val tokens = (0 until maxTokensPerUser).map {
            val createTokenResult = userService.createToken(username, password)
            testClock.advance(1.minutes)

            // then: the creation is successful
            val token = when (createTokenResult) {
                is Either.Left -> fail(createTokenResult.toString())
                is Either.Right -> createTokenResult.value
            }
            token
        }.toTypedArray().reversedArray()

        // and: using the tokens at different times
        (tokens.indices).forEach {
            assertNotNull(userService.getUserByToken(tokens[it].tokenValue), "token $it must be valid")
            testClock.advance(1.seconds)
        }

        // and: creating a new token
        val createTokenResult = userService.createToken(username, password)
        testClock.advance(1.seconds)
        val newToken = when (createTokenResult) {
            is Either.Left -> fail(createTokenResult.toString())
            is Either.Right -> createTokenResult.value
        }

        // then: newToken is valid
        assertNotNull(userService.getUserByToken(newToken.tokenValue))

        // and: the first token (the least recently used) is not valid
        assertNull(userService.getUserByToken(tokens[0].tokenValue))

        // and: the remaining tokens are still valid
        (1 until tokens.size).forEach {
            assertNotNull(userService.getUserByToken(tokens[it].tokenValue))
        }
    }

    @Test
    fun `can limit the number of tokens even if multiple tokens are used at the same time`() {
        // given: a user service
        val testClock = TestClock()
        val maxTokensPerUser = 5
        val userService = createUsersService(testClock, maxTokensPerUser = maxTokensPerUser)

        // when: creating a user
        val username = newTestUserName()
        val password = "changeit"
        val createUserResult = userService.createUser(username, password)

        // then: the creation is successful
        when (createUserResult) {
            is Either.Left -> fail("Unexpected $createUserResult")
            is Either.Right -> assertTrue(createUserResult.value > 0)
        }

        // when: creating MAX tokens
        val tokens = (0 until maxTokensPerUser).map {
            val createTokenResult = userService.createToken(username, password)
            testClock.advance(1.minutes)

            // then: the creation is successful
            val token = when (createTokenResult) {
                is Either.Left -> fail(createTokenResult.toString())
                is Either.Right -> createTokenResult.value
            }
            token
        }.toTypedArray().reversedArray()

        // and: using the tokens at the same time
        testClock.advance(1.minutes)
        (tokens.indices).forEach {
            assertNotNull(userService.getUserByToken(tokens[it].tokenValue), "token $it must be valid")
        }

        // and: creating a new token
        val createTokenResult = userService.createToken(username, password)
        testClock.advance(1.minutes)
        val newToken = when (createTokenResult) {
            is Either.Left -> fail(createTokenResult.toString())
            is Either.Right -> createTokenResult.value
        }

        // then: newToken is valid
        assertNotNull(userService.getUserByToken(newToken.tokenValue))

        // and: exactly one of the previous tokens is now not valid
        assertEquals(
            maxTokensPerUser - 1,
            tokens.count {
                userService.getUserByToken(it.tokenValue) != null
            },
        )
    }

    @Test
    fun `can logout`() {
        // given: a user service
        val testClock = TestClock()
        val maxTokensPerUser = 5
        val userService = createUsersService(testClock, maxTokensPerUser = maxTokensPerUser)

        // when: creating a user
        val username = newTestUserName()
        val password = "changeit"
        val createUserResult = userService.createUser(username, password)

        // then: the creation is successful
        when (createUserResult) {
            is Either.Left -> fail("Unexpected $createUserResult")
            is Either.Right -> assertTrue(createUserResult.value > 0)
        }

        // when: creating a token
        val tokenCreationResult = userService.createToken(username, password)

        // then: token creation is successful
        val token = when (tokenCreationResult) {
            is Either.Left -> fail("Token creation should be successful: '${tokenCreationResult.value}'")
            is Either.Right -> tokenCreationResult.value
        }

        // when: using the token
        var maybeUser = userService.getUserByToken(token.tokenValue)

        // then: token usage is successful
        assertNotNull(maybeUser)

        // when: revoking and using the token
        userService.revokeToken(token.tokenValue)

        maybeUser = userService.getUserByToken(token.tokenValue)

        // then: token usage is successful
        assertNull(maybeUser)
    }

    companion object {
        private fun createUsersService(
            testClock: TestClock,
            tokenTtl: Duration = 30.days,
            tokenRollingTtl: Duration = 30.minutes,
            maxTokensPerUser: Int = 3,
        ) = UsersService(
            JdbiTransactionManager(jdbi),
            UsersDomain(
                BCryptPasswordEncoder(),
                Sha256TokenEncoder(),
                UsersDomainConfig(
                    tokenSizeInBytes = 256 / 8,
                    tokenTtl = tokenTtl,
                    tokenRollingTtl,
                    maxTokensPerUser = maxTokensPerUser,
                ),
            ),
            testClock,
        )

        private fun newTestUserName() = "user-${Math.abs(Random.nextLong())}"

        private val jdbi = Jdbi.create(
            PGSimpleDataSource().apply {
                setURL("jdbc:postgresql://localhost:5432/db?user=dbuser&password=changeit")
            },
        ).configureWithAppRequirements()
    }
}