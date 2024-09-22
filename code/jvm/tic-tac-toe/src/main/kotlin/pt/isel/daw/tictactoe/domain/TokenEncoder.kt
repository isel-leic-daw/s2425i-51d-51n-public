package pt.isel.daw.tictactoe.domain

interface TokenEncoder {
    fun createValidationInformation(token: String): TokenValidationInfo
}