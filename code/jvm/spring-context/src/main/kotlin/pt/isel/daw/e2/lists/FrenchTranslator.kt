package pt.isel.daw.e2.lists

import org.springframework.stereotype.Component

@Component
class FrenchTranslator : LanguageTranslator {
    override val targetLanguage = "fr"

    override fun translate(englishWord: String): String? =
        if (englishWord == "Hello") {
            "Salut"
        } else {
            null
        }
}