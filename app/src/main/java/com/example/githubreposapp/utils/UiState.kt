package com.example.githubreposapp.utils

sealed class UiState<out T> {

    object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val message: String) : UiState<Nothing>()

    companion object {
        /**
         * Returns [State.Loading] instance.
         */
        fun <T> loading(): UiState<T> = Loading

        /**
         * Returns [State.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T): UiState<T> = Success(data)

        /**
         * Returns [State.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error(message: String): UiState<T> = Error(message)
    }

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "There is an error: ${this.message}"
            is Loading -> "Loading"
        }
    }
}