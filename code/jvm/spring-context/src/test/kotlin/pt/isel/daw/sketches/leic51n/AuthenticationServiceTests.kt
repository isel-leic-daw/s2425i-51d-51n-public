package pt.isel.daw.sketches.leic51n

import org.junit.jupiter.api.Assertions.assertFalse
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthenticationServiceTests {
    @Test
    fun first() {
        // given:
        val authenticationService: AuthenticationService =
            createAuthenticationServiceViaContainer()

        // when:
        authenticationService.setPassword("alice", "changeit")
        var result = authenticationService.checkPassword("alice", "changeit")

        // then:
        assertTrue(result)

        // when:
        result = authenticationService.checkPassword("alice", "keepit")

        // then:
        assertFalse(result)
    }

    private fun createAuthenticationService(): AuthenticationService =
        // Manual composition
        AuthenticationService(
            StubPasswordRepository(),
            UnsafePasswordTransformer(),
        )

    private fun createAuthenticationServiceViaContainer(): AuthenticationService {
        val context = AnnotationConfigApplicationContext()
        // component scan
        context.scan("pt.isel.daw.sketches.leic51n")
        context.refresh()
        return context.getBean<AuthenticationService>()
    }

    @Component
    class StubPasswordRepository : PasswordRepository {
        private val map = mutableMapOf<String, PasswordValidation>()

        override fun setPasswordValidation(username: String, pwVal: PasswordValidation) {
            map[username] = pwVal
        }

        override fun getPasswordValidation(username: String): PasswordValidation? {
            return map[username]
        }
    }
}