package pt.isel.daw.e4.beanfunctions

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertContains

class ExampleTests {
    @Test
    fun `can obtain HTTP service and make requests with it`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.scan("pt.isel.daw.e4")
        context.refresh()

        // when:
        val httpClientService = context.getBean<HttpClientService>()
        val response = httpClientService.get("https://httpbin.org/get")

        // then:
        assertContains(response, "\"User-Agent\": \"Java-http-client/")
    }
}