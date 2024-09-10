package pt.isel.daw.e3.lists

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleTests {
    @Test
    fun `can resolve dependency graph`() {
        // given:
        val context = AnnotationConfigApplicationContext()
        context.scan("pt.isel.daw.e3")
        context.refresh()

        // when:
        val translator = context.getBean<Translator>()

        // then:
        assertEquals("Olá", translator.translate("Hello", "pt"))
        assertEquals("Hola", translator.translate("Hello", "es"))
        assertEquals("Hej", translator.translate("Hello", "sv"))
        assertEquals("Salut", translator.translate("Hello", "fr"))
        assertEquals("Hallo", translator.translate("Hello", "de"))
    }
}