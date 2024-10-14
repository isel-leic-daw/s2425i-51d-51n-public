package pt.isel.daw.tictactoe.domain.users

class AuthenticatedUser(
    val user: User,
    val token: String,
)