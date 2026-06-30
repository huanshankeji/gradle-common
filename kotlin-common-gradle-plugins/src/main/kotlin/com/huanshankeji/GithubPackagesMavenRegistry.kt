package com.huanshankeji

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.repositories
import java.net.URI

context(project: Project)
fun MavenArtifactRepository.githubPackagesMavenRegistrySetUrlAndCredentials(owner: String, repository: String) {
    url = project.uri("https://maven.pkg.github.com/$owner/$repository")
    credentials {
        username = project.githubPackagesMavenUsername()
        password = project.githubPackagesMavenPassword()
    }
}

context(settings: Settings)
fun MavenArtifactRepository.githubPackagesMavenRegistrySetUrlAndCredentials(owner: String, repository: String) {
    url = URI("https://maven.pkg.github.com/$owner/$repository")
    credentials {
        username = settings.githubPackagesMavenUsername()
        password = settings.githubPackagesMavenPassword()
    }
}

context(project: Project)
fun RepositoryHandler.mavenGithubPackagesRepository(
    nameArg: String = "GitHubPackages",
    owner: String,
    repository: String,
): MavenArtifactRepository =
    maven {
        name = nameArg
        githubPackagesMavenRegistrySetUrlAndCredentials(owner, repository)
    }

context(settings: Settings)
fun RepositoryHandler.mavenGithubPackagesRepository(
    nameArg: String = "GitHubPackages",
    owner: String,
    repository: String,
): MavenArtifactRepository =
    maven {
        name = nameArg
        githubPackagesMavenRegistrySetUrlAndCredentials(owner, repository)
    }

fun Project.publishingRepositoriesAddGithubPackagesMavenRepository(
    nameArg: String = "GitHubPackages",
    owner: String,
    repository: String,
) {
    with(this) {
        publishing {
            repositories {
                mavenGithubPackagesRepository(nameArg, owner, repository)
            }
        }
    }
}

fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)
