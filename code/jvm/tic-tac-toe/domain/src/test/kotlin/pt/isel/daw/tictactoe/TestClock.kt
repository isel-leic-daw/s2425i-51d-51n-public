package pt.isel.daw.tictactoe

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class TestClock : Clock {
    private var now = Instant.fromEpochSeconds(0)

    override fun now(): Instant = now

    fun advance(duration: Duration) {
        now += duration
    }
}