package pt.isel.daw.tictactoe.domain

class AuthenticatedUser(
    val user: User,
    val token: String,
)