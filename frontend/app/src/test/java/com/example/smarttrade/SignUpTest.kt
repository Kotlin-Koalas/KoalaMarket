package com.example.smarttrade

import com.example.smarttrade.utils.CheckValuesUtils
import org.junit.Test
import junit.framework.TestCase.*

class SignUpTest {
    @Test
    fun testCheckIfValidPassword() {
        assertFalse(CheckValuesUtils.checkIfValidPassword("123"))
        assertFalse(CheckValuesUtils.checkIfValidPassword("pass"))
        assertFalse(CheckValuesUtils.checkIfValidPassword("password123"))
        assertTrue(CheckValuesUtils.checkIfValidPassword("Password123"))
    }

    @Test
    fun testCheckIfValidName() {
        assertFalse(CheckValuesUtils.checkIfValidName("John Doe"))
        assertFalse(CheckValuesUtils.checkIfValidName("John123"))
        assertFalse(CheckValuesUtils.checkIfValidName("!@#"))
        assertTrue(CheckValuesUtils.checkIfValidName("JohnDoe"))
        assertTrue(CheckValuesUtils.checkIfValidName("Alice"))
    }

    @Test
    fun testCheckIfValidDNI() {
        assertFalse(CheckValuesUtils.checkIfValidDNI("1234567A"))
        assertFalse(CheckValuesUtils.checkIfValidDNI("12345678"))
        assertFalse(CheckValuesUtils.checkIfValidDNI("ABCDEFGH"))
        assertTrue(CheckValuesUtils.checkIfValidDNI("12345678A"))
    }

    @Test
    fun testCheckIfValidEmail() {
        assertFalse(CheckValuesUtils.checkIfValidEmail("plainaddress"))
        assertFalse(CheckValuesUtils.checkIfValidEmail("email.example.com"))
        assertFalse(CheckValuesUtils.checkIfValidEmail("email@example@example.com"))
        assertTrue(CheckValuesUtils.checkIfValidEmail("email@example.com"))
        assertTrue(CheckValuesUtils.checkIfValidEmail("firstname.lastname@example.com"))
    }
}