package pt.isel.daw.tictactoe.repository

interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}