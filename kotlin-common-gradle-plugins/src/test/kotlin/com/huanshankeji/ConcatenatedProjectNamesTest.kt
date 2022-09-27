package com.huanshankeji

import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatenatedProjectNamesTest {
    @Test
    fun `test getConcatenatedProjectNamePath`() {
        fun testWithAndWithoutLeadingColon(expected: String, path: String) {
            assertEquals(expected, getConcatenatedProjectNamePath(path))
            assertEquals(":$expected", ":${getConcatenatedProjectNamePath(path)}")
        }

        testWithAndWithoutLeadingColon("a", "a")
        testWithAndWithoutLeadingColon("a:a-b", "a:b")
        testWithAndWithoutLeadingColon("a:a-b:a-b-c", "a:b:c")
    }
}