package pt.isel.daw.tictactoe.repository.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.daw.tictactoe.Environment
import pt.isel.daw.tictactoe.TestClock
import pt.isel.daw.tictactoe.domain.users.PasswordValidationInfo
import pt.isel.daw.tictactoe.domain.users.Token
import pt.isel.daw.tictactoe.domain.users.TokenValidationInfo
import pt.isel.daw.tictactoe.domain.users.User
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

// Don't forget to ensure DBMS is up (e.g. by running ./gradlew dbTestsWait)
class JdbiUserRepositoryTests {
    @Test
    fun `can create and retrieve user`() =
        runWithHandle { handle ->
            // given: a UsersRepository
            val repo = JdbiUsersRepository(handle)

            // when: storing a user
            val userName = newTestUserName()
            val passwordValidationInfo = PasswordValidationInfo(newTokenValidationData())
            repo.storeUser(userName, passwordValidationInfo)

            // and: retrieving a user
            val retrievedUser: User? = repo.getUserByUsername(userName)

            // then:
            assertNotNull(retrievedUser)
            assertEquals(userName, retrievedUser.username)
            assertEquals(passwordValidationInfo, retrievedUser.passwordValidation)
            assertTrue(retrievedUser.id >= 0)

            // when: asking if the user exists
            val isUserIsStored = repo.isUserStoredByUsername(userName)

            // then: response is true
            assertTrue(isUserIsStored)

            // when: asking if a different user exists
            val anotherUserIsStored = repo.isUserStoredByUsername("another-$userName")

            // then: response is false
            assertFalse(anotherUserIsStored)
        }

    @Test
    fun `can create and validate tokens`() =
        runWithHandle { handle ->
            // given: a UsersRepository
            val repo = JdbiUsersRepository(handle)
            // and: a test clock
            val clock = TestClock()

            // and: a createdUser
            val userName = newTestUserName()
            val passwordValidationInfo = PasswordValidationInfo("not-valid")
            val userId = repo.storeUser(userName, passwordValidationInfo)

            // and: test TokenValidationInfo
            val testTokenValidationInfo = TokenValidationInfo(newTokenValidationData())

            // when: creating a token
            val tokenCreationInstant = clock.now()
            val token = Token(
                testTokenValidationInfo,
                userId,
                createdAt = tokenCreationInstant,
                lastUsedAt = tokenCreationInstant,
            )
            repo.createToken(token, 1)

            // then: createToken does not throw errors
            // no exception

            // when: retrieving the token and associated user
            val userAndToken = repo.getTokenByTokenValidationInfo(testTokenValidationInfo)

            // then:
            val (user, retrievedToken) = userAndToken ?: fail("token and associated user must exist")

            // and: ...
            assertEquals(userName, user.username)
            assertEquals(testTokenValidationInfo.validationInfo, retrievedToken.tokenValidationInfo.validationInfo)
            assertEquals(tokenCreationInstant, retrievedToken.createdAt)
        }

    companion object {
        private fun runWithHandle(block: (Handle) -> Unit) = jdbi.useTransaction<Exception>(block)

        private fun newTestUserName() = "user-${abs(Random.nextLong())}"

        private fun newTokenValidationData() = "token-${abs(Random.nextLong())}"

        private val jdbi =
            Jdbi.create(
                PGSimpleDataSource().apply {
                    setURL(Environment.getDbUrl())
                },
            ).configureWithAppRequirements()
    }
}