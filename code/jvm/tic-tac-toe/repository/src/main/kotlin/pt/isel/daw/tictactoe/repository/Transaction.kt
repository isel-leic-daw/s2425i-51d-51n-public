package pt.isel.daw.tictactoe.repository

interface Transaction {
    val usersRepository: UsersRepository

    // other repository types
    fun rollback()
}