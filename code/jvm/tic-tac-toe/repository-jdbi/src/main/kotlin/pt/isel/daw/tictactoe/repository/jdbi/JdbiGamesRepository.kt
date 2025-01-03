package pt.isel.daw.tictactoe.repository.jdbi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.datetime.Instant
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.mapper.Nested
import org.jdbi.v3.core.statement.Update
import org.postgresql.util.PGobject
import pt.isel.daw.tictactoe.domain.games.Board
import pt.isel.daw.tictactoe.domain.games.Game
import pt.isel.daw.tictactoe.domain.users.User
import pt.isel.daw.tictactoe.repository.GamesRepository
import java.util.UUID

class JdbiGamesRepository(
    private val handle: Handle,
) : GamesRepository {
    override fun insert(game: Game) {
        handle.createUpdate(
            """
           insert into dbo.Games(id, state, board, created_at, updated_at, deadline, player_x, player_o) 
           values(:id, :state, :board, :created, :updated, :deadline, :player_x, :player_o)
       """,
        )
            .bind("id", game.id)
            .bind("state", game.state)
            .bindBoard("board", game.board)
            .bind("created", game.created.epochSeconds)
            .bind("updated", game.updated.epochSeconds)
            .bind("deadline", game.deadline?.epochSeconds)
            .bind("player_x", game.playerX.id)
            .bind("player_o", game.playerO.id)
            .execute()
    }

    override fun getById(id: UUID): Game? =
        handle.createQuery(
            """
           select games.id, games.state, games.board, games.created_At, games.updated_at, games.deadline,
             users_x.id as playerX_id, users_x.username as playerX_username, users_x.password_validation as playerX_password_validation,
             users_o.id as playerO_id, users_o.username as playerO_username, users_o.password_validation as playerO_password_validation
           from dbo.Games games  
           inner join dbo.Users users_x on games.player_x = users_x.id
           inner join dbo.Users users_o on games.player_o = users_o.id
           where games.id = :id
        """,
        )
            .bind("id", id)
            .mapTo<GameDbModel>()
            .singleOrNull()
            ?.run {
                toGame()
            }

    override fun update(game: Game) {
        handle.createUpdate(
            """
            update dbo.Games
            set state=:state, board=:board, updated_at=:updated, deadline=:deadline
            where id=:id
        """,
        )
            .bind("id", game.id)
            .bind("state", game.state)
            .bindBoard("board", game.board)
            .bind("updated", game.updated.epochSeconds)
            .bind("deadline", game.deadline?.epochSeconds)
            .execute()
    }

    companion object {
        private fun Update.bindBoard(name: String, board: Board) = run {
            bind(
                name,
                PGobject().apply {
                    type = "jsonb"
                    value = serializeBoardToJson(board)
                },
            )
        }

        private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())

        private fun serializeBoardToJson(board: Board): String = objectMapper.writeValueAsString(board.cells)

        fun deserializeBoardFromJson(json: String) = Board(
            objectMapper.readValue(json, Array<Array<Board.State>>::class.java),
        )
    }
}

data class GameDbModel(
    val id: UUID,
    val state: Game.State,
    val board: Board,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deadline: Instant?,
    @Nested("playerX")
    val playerX: User,
    @Nested("playerO")
    val playerO: User,
) {
    fun toGame() = Game(id, state, board, createdAt, updatedAt, deadline, playerX, playerO)
}