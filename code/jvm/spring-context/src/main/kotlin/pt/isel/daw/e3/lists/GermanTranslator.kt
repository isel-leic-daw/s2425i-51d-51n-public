package pt.isel.daw.e3.lists

import org.springframework.stereotype.Component

@Component
class GermanTranslator : LanguageTranslator {
    override val targetLanguage = "de"

    override fun translate(englishWord: String): String? {
        return if (englishWord == "Hello") {
            "Hallo"
        } else {
            null
        }
    }
}