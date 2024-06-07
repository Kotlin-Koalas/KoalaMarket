package com.example.smarttrade.utils

object CheckValuesUtils {
    fun checkIfValidPassword(password: String): Boolean {
        val patternPassword = "^(?=.{4,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$".toRegex()
        return patternPassword.containsMatchIn(password)
    }

    fun checkIfValidName(name: String): Boolean {
        val patternName = "^[a-zA-Z]*$".toRegex()
        return patternName.containsMatchIn(name)
    }

    fun checkIfValidDNI(DNI: String): Boolean {
        val patternDNI = "[0-9]{8}[a-zA-Z]".toRegex()
        return patternDNI.containsMatchIn(DNI)
    }

    fun checkIfValidEmail(email: String): Boolean {
        val patternEmail = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\$".toRegex()
        return patternEmail.containsMatchIn(email)
    }
}