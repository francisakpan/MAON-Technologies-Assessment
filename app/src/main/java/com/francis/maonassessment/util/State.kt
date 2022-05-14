package com.francis.maonassessment.util

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class State<out R> {
    data class Error(val exception: Exception) : State<Nothing>()
    object Success: State<Nothing>()
    object Loading: State<Nothing>()
    object Idle: State<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Error -> "Error[exception=$exception]"
            Success -> "Successful"
            Loading -> "Loading"
            Idle -> "Idle"
        }
    }
}