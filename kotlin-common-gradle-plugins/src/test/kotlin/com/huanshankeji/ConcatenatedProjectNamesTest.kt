package com.huanshankeji

import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatenatedProjectNamesTest {
    @Test
    fun `test getConcatenatedProjectNamePath`() {
        assertEquals("a", getConcatenatedProjectNamePath("a"))
        assertEquals("a:a-b", getConcatenatedProjectNamePath("a:b"))
        assertEquals("a:a-b:a-b-c", getConcatenatedProjectNamePath("a:b:c"))
    }
}