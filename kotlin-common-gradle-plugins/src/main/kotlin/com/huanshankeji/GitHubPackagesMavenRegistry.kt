package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.repositories

// TODO: use context receivers when it's stable.
fun Project.githubPackagesMavenRegistrySetUrlAndCredentials(
    mavenArtifactRepository: MavenArtifactRepository, owner: String, repository: String
) =
    mavenArtifactRepository.run {
        url = uri("https://maven.pkg.github.com/$owner/$repository")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }

// TODO: use context receivers when it's stable.
fun Project.repositoriesAddGithubPackagesMavenRegistry(owner: String, repository: String) =
    repositories {
        maven {
            githubPackagesMavenRegistrySetUrlAndCredentials(this@maven, owner, repository)
        }
    }
