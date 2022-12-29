package com.huanshankeji

import org.gradle.api.Plugin
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings

fun ProjectDescriptor.setProjectNamesToConcatenated(prefix: String = "") {
    name = prefix + name
    for (child in children)
        child.setProjectNamesToConcatenated("$name-")
}

class SetProjectNamesToConcatenatedPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        println("SetProjectNamesToConcatenatedPlugin test output") // TODO: remove this
        target.rootProject.setProjectNamesToConcatenated()
    }
}
