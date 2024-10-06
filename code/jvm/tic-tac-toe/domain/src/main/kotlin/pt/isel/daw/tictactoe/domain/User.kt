package pt.isel.daw.tictactoe.domain

data class User(
    val id: Int,
    val username: String,
    val passwordValidation: PasswordValidationInfo,
)