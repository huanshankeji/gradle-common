package com.huanshankeji.github.packages.maven

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.findStringProperty
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

@GradleCommonExperimentalApi
fun Project.githubPackagesMavenUsername(): String? =
    findStringProperty("gpr.user") ?: findStringProperty("gprUser")

@GradleCommonExperimentalApi
fun Project.githubPackagesMavenPassword(): String? =
    findStringProperty("gpr.key") ?: findStringProperty("gprKey")


@GradleCommonExperimentalApi
context(project: Project)
fun MavenArtifactRepository.githubPackagesSetUrlAndCredentials(owner: String, repository: String) {
    url = project.uri("https://maven.pkg.github.com/$owner/$repository")
    credentials {
        username = project.githubPackagesMavenUsername()
        password = project.githubPackagesMavenPassword()
    }
}

context(_: Project)
fun RepositoryHandler.githubPackagesMavenRegistry(
    owner: String,
    repository: String
) =
    maven {
        githubPackagesSetUrlAndCredentials(owner, repository)
    }

context(_: Project)
fun RepositoryHandler.githubPackagesMavenRegistryWithName(
    owner: String,
    repository: String,
    name: String = "GitHubPackages"
) =
    maven {
        // Copied and adapted from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry.
        this.name = name
        githubPackagesSetUrlAndCredentials(owner, repository)
    }
