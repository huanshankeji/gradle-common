package com.huanshankeji

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.repositories

context(project: Project)
fun MavenArtifactRepository.githubPackagesMavenRegistrySetUrlAndCredentials(owner: String, repository: String) {
    url = project.uri("https://maven.pkg.github.com/$owner/$repository")
    credentials {
        username = project.githubPackagesMavenUsername()
        password = project.githubPackagesMavenPassword()
    }
}

@Deprecated(
    "Use the context parameter version instead.", // TODO
)
fun Project.repositoriesAddGithubPackagesMavenRegistry(owner: String, repository: String) =
    repositories {
        maven {
            with(this@repositoriesAddGithubPackagesMavenRegistry) {
                githubPackagesMavenRegistrySetUrlAndCredentials(owner, repository)
            }
        }
    }

fun Project.publishingRepositoriesAddGithubPackagesMavenRepository(
    nameArg: String = "GitHubPackages",
    owner: String,
    repository: String,
) =
    publishing {
        repositories {
            maven {
                name = nameArg
                with(this@publishingRepositoriesAddGithubPackagesMavenRepository) {
                    githubPackagesMavenRegistrySetUrlAndCredentials(owner, repository)
                }
            }
        }
    }

fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)
