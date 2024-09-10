package pt.isel.daw.e0.intro

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthenticationServiceTests {
    @Test
    fun first() {
        // given
        val authenticationService = composeAuthenticationServiceUsingSpringContext()
        val userId = UserId("Alice")
        val password = "changeit"
        val notThePassword = "keepit"

        // when
        authenticationService.setPassword(userId, password)

        // then
        assertTrue(authenticationService.checkPassword(userId, password))
        assertFalse(authenticationService.checkPassword(userId, notThePassword))
    }

    companion object {
        private fun composeAuthenticationService(): AuthenticationService =
            AuthenticationService(
                UnsafePasswordTransformer(),
                StubPasswordRepository(),
            )

        private fun composeAuthenticationServiceUsingSpringContext(): AuthenticationService {
            val context = AnnotationConfigApplicationContext()
            context.register(AuthenticationService::class.java)
            context.register(UnsafePasswordTransformer::class.java)
            context.register(StubPasswordRepository::class.java)
            context.refresh()
            return context.getBean<AuthenticationService>()
        }
    }

    class StubPasswordRepository : PasswordRepository {
        private val map = mutableMapOf<UserId, TransformedPassword>()

        override fun read(userId: UserId): TransformedPassword? = map[userId]

        override fun write(userId: UserId, transformedPassword: TransformedPassword) {
            map[userId] = transformedPassword
        }
    }
}