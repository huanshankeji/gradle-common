package com.huanshankeji

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ConcatenatedProjectNamesTest {
    @Test
    fun `test getConcatenatedProjectNamePath`() {
        fun singleTest(expected: String, path: String) {
            assertEquals(expected, getConcatenatedProjectNamePath("p", path))
        }

        singleTest(":p-a", ":a")
        singleTest(":p-a:p-a-b", ":a:b")
        singleTest(":p-a:p-a-b:p-a-b-c", ":a:b:c")

        try {
            getConcatenatedProjectNamePath("p", "a")
            fail()
        } catch (_: IllegalArgumentException) {
        }
    }
}