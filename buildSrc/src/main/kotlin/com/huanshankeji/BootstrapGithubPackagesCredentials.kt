package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

fun Project.resolveGithubPackagesMavenUsername(): String? =
    findProperty("gpr.user") as String?
        ?: findProperty("gprUser") as String?
        ?: providers.environmentVariable("GPR_USER").orNull
        ?: providers.environmentVariable("GITHUB_ACTOR").orNull

fun Project.resolveGithubPackagesMavenPassword(): String? =
    findProperty("gpr.key") as String?
        ?: findProperty("gprKey") as String?
        ?: providers.environmentVariable("GPR_KEY").orNull
        ?: providers.environmentVariable("GITHUB_TOKEN").orNull

fun Project.configureBootstrapGithubPackagesCredentials() {
    afterEvaluate {
        extensions.configure<PublishingExtension>("publishing") {
            repositories.withType<MavenArtifactRepository>().configureEach {
                if (name == "GitHubPackages") {
                    credentials {
                        username = project.resolveGithubPackagesMavenUsername()
                        password = project.resolveGithubPackagesMavenPassword()
                    }
                }
            }
        }
    }
}
