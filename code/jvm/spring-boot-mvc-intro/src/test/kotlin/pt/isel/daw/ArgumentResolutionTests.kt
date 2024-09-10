package pt.isel.daw

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.test.Test

class ArgumentResolutionTests {

    companion object {

        private lateinit var context: ConfigurableApplicationContext

        @JvmStatic
        @BeforeAll
        fun start() {
            println("starting app")
            context = runApplication<Application>()
            waitForServer(InetSocketAddress(InetAddress.getLocalHost(), 8080))
        }

        @JvmStatic
        @AfterAll
        fun stop() {
            context.stop()
        }

        private fun waitForServer(address: InetSocketAddress) {
            val socket = Socket()
            socket.connect(address, 2000)
            socket.close()
        }
    }

    @Test
    fun `getting arguments from the path`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a GET to /examples-ar/0/{id}
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/0/123")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("handler0 with 123")
    }

    @Test
    fun `getting arguments from the query string`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a GET to /examples-ar/1/?id=456
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/1?id=456")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("handler1 with 456")
    }
}