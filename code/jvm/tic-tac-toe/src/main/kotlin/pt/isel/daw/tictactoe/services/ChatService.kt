package pt.isel.daw.tictactoe.services

import jakarta.inject.Named
import kotlinx.datetime.Clock
import org.slf4j.LoggerFactory
import pt.isel.daw.tictactoe.domain.chat.Event
import pt.isel.daw.tictactoe.domain.chat.EventListener
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Named
class ChatService : NeedsShutdowm{

    private val listeners = mutableListOf<EventListener>()
    private var currentId = 0L
    private val lock = ReentrantLock()

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1).also {
        it.scheduleAtFixedRate({ keepAlive() }, 2, 2, TimeUnit.SECONDS)
    }

    override fun shutdown() {
        logger.info("shutting down")
        scheduler.shutdown()
    }

    fun addListner(listener: EventListener) = lock.withLock {
        logger.info("adding listener")
        listeners.add(listener)
        listener.onCompletion {
            logger.info("onCompletion")
            removeListener(listener)
        }
        listener.onError {
            logger.info("onError")
            removeListener(listener)
        }
        listener
    }

    fun sendMessage(msg: String) = lock.withLock {
        logger.info("sendMessage")
        val id = currentId++
        sendEventToAll(Event.Message(id, msg))
    }

    private fun removeListener(listener: EventListener) = lock.withLock {
        logger.info("removing listener")
        listeners.remove(listener)
    }

    private fun keepAlive() = lock.withLock {
        logger.info("keepAlive, sending to {} listeners", listeners.count())
        sendEventToAll(Event.KeepAlive(Clock.System.now()))
    }

    private fun sendEventToAll(event: Event) {
        listeners.forEach {
            try {
                it.send(event)
            } catch (ex: Exception) {
                logger.info("Exception while sending event - {}", ex.message)
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ChatService::class.java)
    }
}
