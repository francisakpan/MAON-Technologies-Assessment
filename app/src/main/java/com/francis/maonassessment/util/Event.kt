package com.francis.maonassessment.util

open class Event<out T>(private val content: T) {

    var handled = false
        private set

    fun getContentIfNotHandled() : T? {
        return if (handled) null
        else {
            handled = true
            content
        }
    }
}