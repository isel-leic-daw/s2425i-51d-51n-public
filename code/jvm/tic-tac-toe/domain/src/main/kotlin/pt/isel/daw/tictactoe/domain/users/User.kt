package pt.isel.daw.tictactoe.domain.users

data class User(
    val id: Int,
    val username: String,
    val passwordValidation: PasswordValidationInfo,
)