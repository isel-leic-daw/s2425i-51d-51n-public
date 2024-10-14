package pt.isel.daw.tictactoe.http.pipeline

import org.springframework.stereotype.Component
import pt.isel.daw.tictactoe.domain.users.AuthenticatedUser
import pt.isel.daw.tictactoe.services.UsersService

@Component
class RequestTokenProcessor(
    val usersService: UsersService,
) {
    fun processAuthorizationHeaderValue(authorizationValue: String?): AuthenticatedUser? {
        if (authorizationValue == null) {
            return null
        }
        val parts = authorizationValue.trim().split(" ")
        if (parts.size != 2) {
            return null
        }
        if (parts[0].lowercase() != SCHEME) {
            return null
        }
        return usersService.getUserByToken(parts[1])?.let {
            AuthenticatedUser(
                it,
                parts[1],
            )
        }
    }

    companion object {
        const val SCHEME = "bearer"
    }
}