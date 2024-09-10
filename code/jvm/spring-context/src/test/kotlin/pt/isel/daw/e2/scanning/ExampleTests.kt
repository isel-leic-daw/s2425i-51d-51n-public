package pt.isel.daw.e2.scanning

import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertNotNull

class ExampleTests {
    @Test
    fun `can resolve dependency graph`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.scan("pt.isel.daw.e2")
        context.refresh()

        // when:
        val componentB = context.getBean<ComponentB>()

        // then:
        assertNotNull(componentB)
        assertTrue(componentB.dependency is ComponentA)
    }
}