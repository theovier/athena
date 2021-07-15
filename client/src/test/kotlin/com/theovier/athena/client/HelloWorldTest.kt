package com.theovier.athena.client

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class HelloWorldTest {

    @Test
    @DisplayName("Hello World Test")
    fun helloWorldTest() {
        val msg = "Hello World!"
        Assertions.assertEquals("Hello World!", msg)
    }
}