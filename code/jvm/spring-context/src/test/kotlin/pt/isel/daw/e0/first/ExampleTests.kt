package pt.isel.daw.e0.first

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertNotNull

class ExampleTests {
    @Test
    fun `can resolve dependency graph`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.register(
            ComponentC::class.java,
            ComponentA::class.java,
            ComponentB::class.java,
        )
        context.refresh()

        // when:
        val componentC = context.getBean<ComponentC>()

        // then:
        assertNotNull(componentC)
    }
}