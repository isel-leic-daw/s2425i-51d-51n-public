package pt.isel.daw.e3.lists

import org.springframework.stereotype.Component

@Component
class DutchTranslator : LanguageTranslator {
    override fun translate(englishWord: String): String? {
        return if (englishWord.lowercase() == "hello") {
            "Hallo"
        } else {
            null
        }
    }

    override val targetLanguage = "nl"
}