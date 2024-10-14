package pt.isel.daw.tictactoe.repository.jdbi

import pt.isel.daw.tictactoe.TestClock
import pt.isel.daw.tictactoe.domain.games.Board
import pt.isel.daw.tictactoe.domain.games.GameLogic
import pt.isel.daw.tictactoe.domain.games.Position
import pt.isel.daw.tictactoe.domain.users.PasswordValidationInfo
import pt.isel.daw.tictactoe.testWithHandleAndRollback
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail
import kotlin.time.Duration.Companion.minutes

class GameRepositoryTests {
    @Test
    fun `can create and retrieve`(): Unit = testWithHandleAndRollback { handle ->

        // given: repositories and logic
        val userRepo = JdbiUsersRepository(handle)
        val gameRepo = JdbiGamesRepository(handle)
        val gameLogic = GameLogic(TestClock(), 5.minutes)

        // and: two existing users
        userRepo.storeUser("alice", PasswordValidationInfo(""))
        userRepo.storeUser("bob", PasswordValidationInfo(""))

        // when: creating and inserting a game
        val alice = userRepo.getUserByUsername("alice") ?: fail("user must exist")
        val bob = userRepo.getUserByUsername("bob") ?: fail("user must exist")
        val game = gameLogic.createNewGame(alice, bob)
        gameRepo.insert(game)

        // and: retrieving the game
        val retrievedGame = gameRepo.getById(game.id)

        // then: the two games are equal
        assertEquals(game, retrievedGame)

        // when: updating the game
        val newGame = game.copy(board = game.board.mutate(Position(1, 1), Board.State.PLAYER_X))

        // and: storing the game
        gameRepo.update(newGame)

        // and: retrieving the game again
        val newRetrievedGame = gameRepo.getById(game.id)

        // then: the two games are equal
        assertEquals(newGame, newRetrievedGame)
    }
}