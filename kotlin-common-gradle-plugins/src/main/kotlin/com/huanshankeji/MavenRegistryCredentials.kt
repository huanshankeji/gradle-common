package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.initialization.Settings

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull

fun Project.githubPackagesMavenUsername(): String? =
    findStringProperty("gpr.user") ?: findStringProperty("gprUser")
        ?: System.getenv("USERNAME")

fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey")
        ?: System.getenv("TOKEN")

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")
