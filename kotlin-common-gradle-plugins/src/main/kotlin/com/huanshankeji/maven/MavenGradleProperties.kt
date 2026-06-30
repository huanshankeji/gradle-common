package com.huanshankeji.maven

import org.gradle.api.Project
import org.gradle.api.initialization.Settings

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull
