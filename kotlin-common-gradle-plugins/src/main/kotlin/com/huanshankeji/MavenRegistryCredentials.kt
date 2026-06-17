package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.initialization.Settings
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.credentials

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull

fun Project.githubPackagesMavenUsername(): String? =
    findStringProperty("gpr.user") ?: findStringProperty("gprUser")

fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey")

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")

internal fun Project.githubPackagesMavenUsernameProvider(): Provider<String> =
    providers.gradleProperty("gpr.user")
        .orElse(providers.gradleProperty("gprUser"))
        .orElse(providers.environmentVariable("GPR_USER"))
        .orElse(providers.environmentVariable("GITHUB_ACTOR"))

internal fun Project.githubPackagesMavenPasswordProvider(): Provider<String> =
    providers.gradleProperty("gpr.key")
        .orElse(providers.gradleProperty("gprKey"))
        .orElse(providers.environmentVariable("GPR_KEY"))
        .orElse(providers.environmentVariable("GITHUB_TOKEN"))

internal fun Settings.githubPackagesMavenUsernameProvider(): Provider<String> =
    providers.gradleProperty("gpr.user")
        .orElse(providers.gradleProperty("gprUser"))
        .orElse(providers.environmentVariable("GPR_USER"))
        .orElse(providers.environmentVariable("GITHUB_ACTOR"))

internal fun Settings.githubPackagesMavenPasswordProvider(): Provider<String> =
    providers.gradleProperty("gpr.key")
        .orElse(providers.gradleProperty("gprKey"))
        .orElse(providers.environmentVariable("GPR_KEY"))
        .orElse(providers.environmentVariable("GITHUB_TOKEN"))

context(project: Project)
fun MavenArtifactRepository.configureGithubPackagesMavenCredentials() {
    credentials(PasswordCredentials::class) {
        username.set(project.githubPackagesMavenUsernameProvider())
        password.set(project.githubPackagesMavenPasswordProvider())
    }
}

context(settings: Settings)
fun MavenArtifactRepository.configureGithubPackagesMavenCredentials() {
    credentials(PasswordCredentials::class) {
        username.set(settings.githubPackagesMavenUsernameProvider())
        password.set(settings.githubPackagesMavenPasswordProvider())
    }
}
