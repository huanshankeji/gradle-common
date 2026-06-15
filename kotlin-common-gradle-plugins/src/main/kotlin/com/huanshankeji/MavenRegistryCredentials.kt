package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.Logging

private val debugLog = Logging.getLogger("com.huanshankeji.MavenRegistryCredentials")

// TODO remove debugging code
private fun logDebugCredentialsContext(label: String, properties: Map<*, *>) {
    debugLog.warn("=== Debug ($label): properties ===")
    properties.entries
        .map { it.key.toString() to it.value }
        .sortedBy { it.first }
        .forEach { (key, value) -> debugLog.warn("  $key = $value") }
    debugLog.warn("=== Debug ($label): environment variables ===")
    System.getenv().toSortedMap().forEach { (key, value) -> debugLog.warn("  $key = $value") }
}

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull

fun Project.githubPackagesMavenUsername(): String? =
    run {
        logDebugCredentialsContext("project", properties)
        findStringProperty("gpr.user") ?: findStringProperty("gprUser") //?: System.getenv("USERNAME")
    }

fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey") //?: System.getenv("TOKEN")

fun Settings.githubPackagesMavenUsername(): String? =
    run {
        logDebugCredentialsContext("settings", gradle.startParameter.projectProperties)
        findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")
    }

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")
