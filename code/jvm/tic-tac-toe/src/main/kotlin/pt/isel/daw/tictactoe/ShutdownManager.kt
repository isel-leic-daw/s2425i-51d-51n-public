package pt.isel.daw.tictactoe

import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Component
import pt.isel.daw.tictactoe.services.NeedsShutdowm

@Component
class ShutdownManager(
    private val needShutdown: List<NeedsShutdowm>
) {
    @PreDestroy
    private fun preDestroy() {
        needShutdown.forEach {
            it.shutdown()
        }
    }
}