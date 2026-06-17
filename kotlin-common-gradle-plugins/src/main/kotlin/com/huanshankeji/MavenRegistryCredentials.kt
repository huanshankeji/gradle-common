package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings

fun Project.findStringProperty(propertyName: String): String? =
    findProperty(propertyName) as String?

fun Settings.findStringGradleProperty(propertyName: String): String? =
    providers.gradleProperty(propertyName).orNull

fun Project.githubPackagesMavenUsername(): String? =
    findStringProperty("gpr.user") ?: findStringProperty("gprUser")
        ?: providers.environmentVariable("GPR_USER").orNull
        ?: providers.environmentVariable("GITHUB_ACTOR").orNull

fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey")
        ?: providers.environmentVariable("GPR_KEY").orNull
        ?: providers.environmentVariable("GITHUB_TOKEN").orNull

fun Settings.githubPackagesMavenUsername(): String? =
    findStringGradleProperty("gpr.user") ?: findStringGradleProperty("gprUser")
        ?: providers.environmentVariable("GPR_USER").orNull
        ?: providers.environmentVariable("GITHUB_ACTOR").orNull

fun Settings.githubPackagesMavenPassword(): String? =
    findStringGradleProperty("gpr.key") ?: findStringGradleProperty("gprKey")
        ?: providers.environmentVariable("GPR_KEY").orNull
        ?: providers.environmentVariable("GITHUB_TOKEN").orNull

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")

context(project: Project)
fun MavenArtifactRepository.configureGithubPackagesMavenCredentials() {
    credentials {
        username = project.githubPackagesMavenUsername()
        password = project.githubPackagesMavenPassword()
    }
}

context(settings: Settings)
fun MavenArtifactRepository.configureGithubPackagesMavenCredentials() {
    credentials {
        username = settings.githubPackagesMavenUsername()
        password = settings.githubPackagesMavenPassword()
    }
}
