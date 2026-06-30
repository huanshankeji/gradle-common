package com.huanshankeji.github.packages

import com.huanshankeji.maven.publishing
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings
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

@Deprecated(
    "Use the context parameter version instead.",
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