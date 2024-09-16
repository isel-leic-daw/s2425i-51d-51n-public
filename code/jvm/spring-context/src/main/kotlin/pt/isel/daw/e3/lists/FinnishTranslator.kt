package pt.isel.daw.e3.lists

import org.springframework.stereotype.Component

@Component
class FinnishTranslator : LanguageTranslator {
    override fun translate(englishWord: String): String? {
        return if (englishWord.lowercase() == "hello") {
            "Moi"
        } else {
            null
        }
    }

    override val targetLanguage: String = "fi"
}