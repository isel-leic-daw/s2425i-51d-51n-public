package pt.isel.daw.tictactoe.domain

interface EventEmitter {
    fun emit(event: Event)

    fun onCompletion(callback: () -> Unit)

    fun onError(callback: (Throwable) -> Unit)
}