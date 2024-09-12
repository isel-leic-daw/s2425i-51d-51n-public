package pt.isel.daw.sketches.leic51d

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthenticationServiceTests {
    @Test
    fun first() {
        // given:
        val authenticationService: AuthenticationService =
            createAuthenticationServiceViaAContainer()

        // when:
        authenticationService.setPassword("alice", "changeit")
        val matches = authenticationService.checkPassword("alice", "changeit")

        // then:
        assertTrue(matches)

        // when:
        val secondMatch = authenticationService.checkPassword("alice", "keepit")

        // then:
        assertFalse(secondMatch)
    }

    private fun createAuthenticationServiceManually(): AuthenticationService =
        // Composition ("manually")
        AuthenticationService(
            UnsafePasswordTransformer(),
            TestPasswordRepository(),
        )

    private fun createAuthenticationServiceViaAContainer(): AuthenticationService {
        val container = AnnotationConfigApplicationContext()
        container.register(
            AuthenticationService::class.java,
            UnsafePasswordTransformer::class.java,
            TestPasswordRepository::class.java,
        )
        container.refresh()
        return container.getBean<AuthenticationService>()
    }

    class TestPasswordRepository : PasswordRepository {
        private val map = mutableMapOf<String, TransformedPassword>()

        override fun set(username: String, transformedPassword: TransformedPassword) {
            map[username] = transformedPassword
        }

        override fun get(username: String): TransformedPassword? =
            map[username]
    }
}