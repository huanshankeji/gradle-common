package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.credentials
import org.gradle.kotlin.dsl.withType

fun Project.configureBootstrapGithubPackagesCredentials() {
    afterEvaluate {
        extensions.configure<PublishingExtension>("publishing") {
            repositories.withType<MavenArtifactRepository>().configureEach {
                if (name == "GitHubPackages") {
                    credentials(PasswordCredentials::class) {
                        username.set(
                            project.providers.gradleProperty("gpr.user")
                                .orElse(project.providers.gradleProperty("gprUser"))
                                .orElse(project.providers.environmentVariable("GPR_USER"))
                                .orElse(project.providers.environmentVariable("GITHUB_ACTOR")),
                        )
                        password.set(
                            project.providers.gradleProperty("gpr.key")
                                .orElse(project.providers.gradleProperty("gprKey"))
                                .orElse(project.providers.environmentVariable("GPR_KEY"))
                                .orElse(project.providers.environmentVariable("GITHUB_TOKEN")),
                        )
                    }
                }
            }
        }
    }
}
