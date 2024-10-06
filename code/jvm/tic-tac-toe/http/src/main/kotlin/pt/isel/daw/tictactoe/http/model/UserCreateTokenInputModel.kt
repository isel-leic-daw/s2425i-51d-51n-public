package pt.isel.daw.tictactoe.http.model

data class UserCreateTokenInputModel(
    val username: String,
    val password: String,
)