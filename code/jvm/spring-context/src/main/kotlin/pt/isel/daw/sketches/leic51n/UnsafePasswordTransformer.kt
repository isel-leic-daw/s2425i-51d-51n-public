package pt.isel.daw.sketches.leic51n

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.Base64

/**
 * Unsage password transformer, just used for demo purposes.
 *
 * Just computing the hash of the password is not enough.
 */
@Component
class UnsafePasswordTransformer : PasswordTransformer {
    override fun transform(password: String): PasswordValidation {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val clearPasswordBytes = password.toByteArray(CHARSET)
        val hashedBytes = sha256.digest(clearPasswordBytes)
        val transformedPassword =
            PasswordValidation(
                Base64.getUrlEncoder().encodeToString(hashedBytes),
            )
        return transformedPassword
    }

    override fun match(password: String, passwordValidation: PasswordValidation): Boolean {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val clearPasswordBytes = password.toByteArray(CHARSET)
        val hashedBytes = sha256.digest(clearPasswordBytes)
        val transformedPasswordString = Base64.getUrlEncoder().encodeToString(hashedBytes)

        return transformedPasswordString == passwordValidation.value
    }

    companion object {
        private val CHARSET = Charsets.UTF_8
    }
}