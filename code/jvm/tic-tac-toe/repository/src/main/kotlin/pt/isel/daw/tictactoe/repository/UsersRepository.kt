package pt.isel.daw.tictactoe.repository

import kotlinx.datetime.Instant
import pt.isel.daw.tictactoe.domain.users.PasswordValidationInfo
import pt.isel.daw.tictactoe.domain.users.Token
import pt.isel.daw.tictactoe.domain.users.TokenValidationInfo
import pt.isel.daw.tictactoe.domain.users.User

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