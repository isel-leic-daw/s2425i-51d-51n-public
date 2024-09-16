package pt.isel.daw.sketches.leic51n

import org.springframework.stereotype.Component

// using the class construct to create a transparent data holder
data class Pair<T, U>(
    val first: T,
    val second: U,
)

// using the class construct to create a holder of functionality
@Component
class AuthenticationService(
    private val passwordRepository: PasswordRepository,
    private val passwordTransformer: PasswordTransformer,
) {
    fun setPassword(username: String, password: String) {
        passwordRepository.setPasswordValidation(
            username,
            passwordTransformer.transform(password),
        )
    }

    fun checkPassword(username: String, password: String): Boolean {
        val validationInfo =
            passwordRepository.getPasswordValidation(username)
                ?: return false
        return passwordTransformer.match(password, validationInfo)
    }
}

data class PasswordValidation(
    val value: String,
)

interface PasswordRepository {
    fun setPasswordValidation(username: String, pwVal: PasswordValidation)

    fun getPasswordValidation(username: String): PasswordValidation?
}

interface PasswordTransformer {
    fun transform(password: String): PasswordValidation

    fun match(password: String, passwordValidation: PasswordValidation): Boolean
}