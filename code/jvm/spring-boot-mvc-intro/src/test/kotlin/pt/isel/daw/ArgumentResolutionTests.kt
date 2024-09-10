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

        // when: doing a GET
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

        // when: doing a GET
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/1?id=456")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("handler1 with 456")
    }

    @Test
    fun `getting optional arguments from the query string`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a GET
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/2?id=456")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("handler2 with 456")

        // when: doing a GET
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/2?not-id=456")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("handler2 with absent")
    }

    @Test
    fun `getting all arguments from the query string`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a GET
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/3?id=123&id=abc&foo=bar")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("id: [123, abc], foo: [bar]")
    }

    @Test
    fun `accessing the HttpServletRequest directly`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a GET
        // then: the response is an OK with the expected string
        testClient
            .get()
            .uri("/examples-ar/4?id=123&id=abc&foo=bar")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("Hello2 127.0.0.1")
    }

    @Test
    fun `accessing a deserialized object`() {
        // given: a test client
        val testClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build()

        // when: doing a POST with a valid body
        // then: the response is an OK with the expected string
        testClient
            .post()
            .uri("/examples-ar/5")
            .bodyValue(
                mapOf(
                    "name" to "Alice",
                    "number" to "1",
                    "enrollmentYear" to "2024",
                ),
            )
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("Received student with name=Alice, number=1, and enrollment year=2024")

        // when: doing a POST with an invalid body
        // then: the response is a 4xx
        testClient
            .post()
            .uri("/examples-ar/5")
            .bodyValue(
                mapOf(
                    "name" to "Alice",
                    "number" to "1",
                    "enrollmentYear" to "1900",
                ),
            )
            .exchange()
            .expectStatus().isBadRequest
    }
}