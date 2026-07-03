package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.initialization.Settings

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

// TODO remove this if not needed
fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull
