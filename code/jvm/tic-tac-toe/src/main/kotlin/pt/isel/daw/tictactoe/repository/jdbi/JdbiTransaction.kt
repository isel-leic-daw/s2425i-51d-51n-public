package pt.isel.daw.tictactoe.repository.jdbi

import org.jdbi.v3.core.Handle
import pt.isel.daw.tictactoe.repository.Transaction
import pt.isel.daw.tictactoe.repository.UsersRepository

class JdbiTransaction(
    private val handle: Handle,
) : Transaction {
    override val usersRepository: UsersRepository = JdbiUsersRepository(handle)

    override fun rollback() {
        handle.rollback()
    }
}