package pt.isel.daw.e5.scopes

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTests {
    @Test
    fun `component scope is correct`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.scan("pt.isel.daw.e5")
        context.refresh()

        // when:
        val d = context.getBean<ComponentD>()

        // then:
        assertEquals(3, setOf(d.a, d.b.a, d.c.a).size)
        assertEquals(1, setOf(d.b, d.c.b).size)
    }
}