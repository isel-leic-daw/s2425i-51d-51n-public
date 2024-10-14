package pt.isel.daw.tictactoe.domain.games

import pt.isel.daw.tictactoe.domain.users.User

class Round(
    val position: Position,
    val player: User,
)