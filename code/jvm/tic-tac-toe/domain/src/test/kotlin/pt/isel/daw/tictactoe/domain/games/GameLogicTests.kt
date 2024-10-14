package pt.isel.daw.tictactoe.domain.games

import kotlinx.datetime.Clock
import pt.isel.daw.tictactoe.TestClock
import pt.isel.daw.tictactoe.domain.users.PasswordValidationInfo
import pt.isel.daw.tictactoe.domain.users.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GameLogicTests {
    @Test
    fun `simple game`() {
        // given:a game
        var game = gameLogic.createNewGame(alice, bob)

        // when: alice plays
        var result = gameLogic.applyRound(game, Round(Position(1, 1), alice))

        // then: next player is bob
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_O, game.state)

        // when: bob plays
        result = gameLogic.applyRound(game, Round(Position(0, 1), bob))

        // then: next player is alice
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_X, game.state)

        // when: alice plays
        result = gameLogic.applyRound(game, Round(Position(0, 0), alice))

        // then: next player is bob
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_O, game.state)

        // when: bob plays
        result = gameLogic.applyRound(game, Round(Position(2, 1), bob))

        // then: next player is alice
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_X, game.state)

        // when: alice plays
        result = gameLogic.applyRound(game, Round(Position(2, 2), alice))

        // then: alice wins
        game = when (result) {
            is RoundResult.YouWon -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.PLAYER_X_WON, game.state)
    }

    @Test
    fun `cannot play twice`() {
        // given: a game
        var game = gameLogic.createNewGame(alice, bob)

        // when: alice plays
        var result = gameLogic.applyRound(game, Round(Position(1, 1), alice))

        // then: next player is bob
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_O, game.state)

        // when: bob plays
        result = gameLogic.applyRound(game, Round(Position(2, 1), bob))

        // then: next player is alice
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_X, game.state)

        // when: bob plays
        result = gameLogic.applyRound(game, Round(Position(2, 1), bob))

        // then: result is a failure and next player is still alice
        when (result) {
            is RoundResult.NotYourTurn -> {}
            else -> fail("Unexpected round result $result")
        }
        assertEquals(Game.State.NEXT_PLAYER_X, game.state)
    }

    @Test
    fun `alice wins`() {
        // given: a game and a list of rounds
        val game = gameLogic.createNewGame(alice, bob)
        val rounds = listOf(
            Round(Position(1, 1), alice),
            Round(Position(0, 1), bob),
            Round(Position(0, 0), alice),
            Round(Position(2, 2), bob),
            Round(Position(2, 0), alice),
            Round(Position(0, 2), bob),
            Round(Position(1, 0), alice),
        )

        // when: the rounds are applied
        val result = play(gameLogic, game, rounds)

        // then: alice wins
        when (result) {
            is RoundResult.YouWon -> {
                assertEquals(Game.State.PLAYER_X_WON, result.game.state)
            }
            else -> fail("Unexpected round result $result")
        }
    }

    @Test
    fun `test draw game`() {
        // given: a game and a list of rounds
        val game = gameLogic.createNewGame(alice, bob)
        val rounds = listOf(
            Round(Position(0, 0), alice),
            Round(Position(1, 1), bob),
            Round(Position(2, 0), alice),
            Round(Position(1, 0), bob),
            Round(Position(1, 2), alice),
            Round(Position(0, 1), bob),
            Round(Position(2, 1), alice),
            Round(Position(2, 2), bob),
            Round(Position(0, 2), alice),
        )

        // when: the rounds are applied
        val result = play(gameLogic, game, rounds)

        // then: it's a draw
        when (result) {
            is RoundResult.Draw -> {
                assertEquals(Game.State.DRAW, result.game.state)
            }
            else -> fail("Unexpected round result $result")
        }
    }

    @Test
    fun `timeout test`() {
        // given: a game logic, a game and a list of rounds
        val testClock = TestClock()
        val timeout = 5.minutes
        val gameLogic = GameLogic(testClock, timeout)
        var game = gameLogic.createNewGame(alice, bob)

        // when: alice plays
        testClock.advance(timeout.minus(1.minutes))
        var result = gameLogic.applyRound(game, Round(Position(1, 1), alice))

        // then: round is accepted
        game = when (result) {
            is RoundResult.OthersTurn -> result.game
            else -> fail("Unexpected result $result")
        }

        // when: bob plays
        testClock.advance(timeout.plus(1.seconds))
        result = gameLogic.applyRound(game, Round(Position(1, 1), bob))

        // then: round is not accepted and alice won
        game = when (result) {
            is RoundResult.TooLate -> result.game
            else -> fail("Unexpected result $result")
        }
        assertEquals(Game.State.PLAYER_X_WON, game.state)
    }

    private fun play(logic: GameLogic, initialGame: Game, rounds: List<Round>): RoundResult? {
        var previousResult: RoundResult? = null
        for (round in rounds) {
            val game = when (previousResult) {
                null -> initialGame
                is RoundResult.OthersTurn -> previousResult.game
                else -> fail("Unexpected round result $previousResult")
            }
            previousResult = logic.applyRound(game, round)
        }
        return previousResult
    }

    companion object {
        private val gameLogic = GameLogic(
            Clock.System,
            5.minutes,
        )

        // our test players
        private val alice = User(1, "alice", PasswordValidationInfo(""))
        private val bob = User(2, "alice", PasswordValidationInfo(""))
    }
}