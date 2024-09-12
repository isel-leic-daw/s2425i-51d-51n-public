package pt.isel.daw.sketches.leic51d

/*
 * Inversion of Control (IoC)
 * Dependency Injection (DI)
 * IoC/DI containers
 */

// Holder of transparent data
data class Pair<T, U>(
    val first: T,
    val second: U,
)

/*
 * Holder of functionality
 *
 * Decompose into two sub-services
 * * How to store information
 * * How to transform passwords
 */
class AuthenticationService(
    private val passwordTransformer: PasswordTransformer,
    private val passwordRepository: PasswordRepository,
) {
    fun setPassword(username: String, password: String) {
        passwordRepository.set(username, passwordTransformer.transform(password))
    }

    fun checkPassword(username: String, password: String): Boolean {
        val transformedPassword =
            passwordRepository.get(username)
                ?: return false
        return passwordTransformer.check(password, transformedPassword)
    }
}

data class TransformedPassword(
    val value: String,
)

interface PasswordTransformer {
    fun transform(password: String): TransformedPassword

    fun check(password: String, transformedPassword: TransformedPassword): Boolean
}

interface PasswordRepository {
    fun set(username: String, transformedPassword: TransformedPassword)

    fun get(username: String): TransformedPassword?
}