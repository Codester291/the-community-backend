package com.tolani.incorporated.exceptions

class CitizenNotFoundException(override var message: String) : Exception() {
    init {
        message = "Hello Exception"
    }
}