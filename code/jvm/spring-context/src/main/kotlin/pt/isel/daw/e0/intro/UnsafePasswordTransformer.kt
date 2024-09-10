package pt.isel.daw.e0.intro

import java.security.MessageDigest
import java.util.Base64

/**
 * Unsage password transformer, just used for demo purposes.
 *
 * Just computing the hash of the password is not enough.
 */
class UnsafePasswordTransformer : PasswordTransformer {
    override fun transform(clearPassword: String): TransformedPassword {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val clearPasswordBytes = clearPassword.toByteArray(CHARSET)
        val hashedBytes = sha256.digest(clearPasswordBytes)
        val transformedPassword =
            TransformedPassword(
                Base64.getUrlEncoder().encodeToString(hashedBytes),
            )
        return transformedPassword
    }

    override fun matches(clearPassword: String, transformedPassword: TransformedPassword): Boolean {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val clearPasswordBytes = clearPassword.toByteArray(CHARSET)
        val hashedBytes = sha256.digest(clearPasswordBytes)
        val transformedPasswordString = Base64.getUrlEncoder().encodeToString(hashedBytes)

        return transformedPasswordString == transformedPassword.value
    }

    companion object {
        private val CHARSET = Charsets.UTF_8
    }
}