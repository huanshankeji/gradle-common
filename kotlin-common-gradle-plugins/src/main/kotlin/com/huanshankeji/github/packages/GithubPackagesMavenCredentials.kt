package com.huanshankeji.github.packages

import com.huanshankeji.maven.findStringGradleProperty
import com.huanshankeji.maven.findStringProperty
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

fun Project.githubPackagesMavenUsername(): String? =
    findStringProperty("gpr.user") ?: findStringProperty("gprUser")

fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey")

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")
