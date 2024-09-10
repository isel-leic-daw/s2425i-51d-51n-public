package pt.isel.daw.e0.intro

interface PasswordTransformer {
    fun transform(clearPassword: String): TransformedPassword

    fun matches(clearPassword: String, transformedPassword: TransformedPassword): Boolean
}