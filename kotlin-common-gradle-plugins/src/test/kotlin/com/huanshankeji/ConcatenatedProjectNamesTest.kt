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

    @Test
    fun `setProjectConcatenatedNames renames descriptors consistently with getConcatenatedProjectNamePath`() {
        val root = FakeProjectDescriptor("p")
        val a = root.child("a")
        val b = a.child("b")

        root.setProjectConcatenatedNames("")

        assertEquals("p", root.getName())
        assertEquals("p-a", a.getName())
        assertEquals("p-a-b", b.getName())
        assertEquals(":p-a:p-a-b", getConcatenatedProjectNamePath("p", ":a:b"))
    }
}

private class FakeProjectDescriptor(initialName: String) : org.gradle.api.initialization.ProjectDescriptor {
    private var descriptorName = initialName
    private val childDescriptors = linkedSetOf<FakeProjectDescriptor>()

    fun child(name: String) = FakeProjectDescriptor(name).also { childDescriptors.add(it) }

    override fun getName(): String = descriptorName

    override fun setName(name: String) {
        descriptorName = name
    }

    override fun getProjectDir() = throw UnsupportedOperationException()

    override fun setProjectDir(dir: java.io.File) = throw UnsupportedOperationException()

    override fun getBuildFile() = throw UnsupportedOperationException()

    override fun getBuildFileName() = throw UnsupportedOperationException()

    override fun setBuildFileName(name: String) = throw UnsupportedOperationException()

    override fun getParent(): org.gradle.api.initialization.ProjectDescriptor? = null

    override fun getChildren(): MutableSet<org.gradle.api.initialization.ProjectDescriptor> =
        childDescriptors as MutableSet<org.gradle.api.initialization.ProjectDescriptor>

    override fun getPath(): String = ":$descriptorName"
}