package com.huanshankeji.github.packages.maven

import com.huanshankeji.findStringGradleProperty
import org.gradle.api.initialization.Settings


// TODO remove/move these `Settings` extensions?

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")
