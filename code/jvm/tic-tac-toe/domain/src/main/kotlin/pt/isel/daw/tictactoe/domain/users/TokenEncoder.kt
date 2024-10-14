package pt.isel.daw.tictactoe.domain.users

interface TokenEncoder {
    fun createValidationInformation(token: String): TokenValidationInfo
}