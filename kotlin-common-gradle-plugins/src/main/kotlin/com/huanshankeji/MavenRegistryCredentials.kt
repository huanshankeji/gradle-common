package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.initialization.Settings


// TODO remove these `Settings` extensions?

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")
