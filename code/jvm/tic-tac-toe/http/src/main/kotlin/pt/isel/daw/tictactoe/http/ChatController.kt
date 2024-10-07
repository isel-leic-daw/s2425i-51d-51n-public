package pt.isel.daw.tictactoe.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import pt.isel.daw.tictactoe.services.ChatService
import java.util.concurrent.TimeUnit

@RestController
class ChatController(
    private val chatService: ChatService,
) {
    @GetMapping(Uris.Chat.LISTEN)
    fun listen(): SseEmitter {
        val sseEmitter = SseEmitter(TimeUnit.HOURS.toMillis(1))
        chatService.addEventEmitter(
            SseEmitterBasedEventEmitter(
                sseEmitter,
            ),
        )
        return sseEmitter
    }

    @PostMapping(Uris.Chat.SEND)
    fun send(
        @RequestBody message: String,
    ) {
        chatService.sendMessage(message)
    }
}