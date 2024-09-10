package pt.isel.daw.e0.intro

class AuthenticationService(
    private val passwordTransformer: PasswordTransformer,
    private val passwordRepository: PasswordRepository,
) {
    fun setPassword(
        userId: UserId,
        password: String,
    ) {
        val transformedPassword = passwordTransformer.transform(password)
        passwordRepository.write(userId, transformedPassword)
    }

    fun checkPassword(userId: UserId, password: String): Boolean {
        val transformedPassword = passwordRepository.read(userId) ?: return false
        return passwordTransformer.matches(password, transformedPassword)
    }
}