package com.huanshankeji

import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsConcatenatedProjectNamesTest {
    /*
     * Added by an AI agent (Cursor); not reviewed line by line. Gradle has ProjectBuilder (Project)
     * and TestKit (full builds), but no official fixture for ProjectDescriptor; see
     * FakeProjectDescriptor below.
     */
    @Test
    fun `setProjectConcatenatedNames renames descriptors with parent prefixes`() {
        val root = FakeProjectDescriptor("p")
        val a = root.child("a")
        val b = a.child("b")

        root.setProjectConcatenatedNames("")

        assertEquals("p", root.getName())
        assertEquals("p-a", a.getName())
        assertEquals("p-a-b", b.getName())
    }
}

/*
 * Added by an AI agent (Cursor); not reviewed line by line. Minimal
 * ProjectDescriptor for unit tests. Gradle's testing docs cover ProjectBuilder and TestKit only;
 * there is no SettingsBuilder or ProjectDescriptor factory. setProjectConcatenatedNames needs just
 * name, setName, and children, but the interface requires stubbing everything else.
 */
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
