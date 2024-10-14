package pt.isel.daw.tictactoe.repository

import pt.isel.daw.tictactoe.domain.games.Game
import java.util.UUID

interface GamesRepository {
    fun insert(game: Game)

    fun getById(id: UUID): Game?

    fun update(game: Game)
}