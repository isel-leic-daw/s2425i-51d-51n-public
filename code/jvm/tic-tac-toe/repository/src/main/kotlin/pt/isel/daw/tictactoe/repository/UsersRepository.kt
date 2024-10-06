package pt.isel.daw.tictactoe.repository

import kotlinx.datetime.Instant
import pt.isel.daw.tictactoe.domain.PasswordValidationInfo
import pt.isel.daw.tictactoe.domain.Token
import pt.isel.daw.tictactoe.domain.TokenValidationInfo
import pt.isel.daw.tictactoe.domain.User

interface UsersRepository {
    fun storeUser(
        username: String,
        passwordValidation: PasswordValidationInfo,
    ): Int

    fun getUserByUsername(username: String): User?

    fun getTokenByTokenValidationInfo(tokenValidationInfo: TokenValidationInfo): Pair<User, Token>?

    fun isUserStoredByUsername(username: String): Boolean

    fun createToken(token: Token, maxTokens: Int)

    fun updateTokenLastUsed(token: Token, now: Instant)

    fun removeTokenByValidationInfo(tokenValidationInfo: TokenValidationInfo): Int
}