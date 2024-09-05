package pt.isel.daw.e3.router

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTests {
    @Test
    fun `can resolve all controllers and handlers`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.scan("pt.isel.daw.e3")
        context.refresh()

        // when:
        val router = context.getBean<Router>()

        // then:
        assertEquals(
            setOf(SomeController::class.java, AnotherController::class.java),
            router.controllers.map { it.javaClass }.toSet(),
        )
    }
}