package pt.isel.daw.e0.intro

interface PasswordRepository {
    fun read(userId: UserId): TransformedPassword?

    fun write(userId: UserId, transformedPassword: TransformedPassword)
}