package com.huanshankeji

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.create
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


fun Project.publishingRepositoriesAddGithubPackagesMavenRepository(
    nameArg: String = "GitHubPackages",
    owner: String,
    repository: String
) =
    // Copied and adapted from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry.
    publishing {
        repositories {
            maven {
                name = nameArg
                githubPackagesMavenRegistrySetUrlAndCredentials(this@maven, owner, repository)
            }
        }
    }

// copied and adapted from generated code
fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)

class GithubPackagesMavenPublishPlugin : Plugin<Project> {
    interface Extension {
        val owner: Property<String>
        val repository: Property<String>
    }

    override fun apply(project: Project) = project.run {
        pluginManager.apply("maven-publish")

        val extension = extensions.create<Extension>("githubPackagesPublish")

        afterEvaluate {
            publishingRepositoriesAddGithubPackagesMavenRepository(
                owner = extension.owner.get(),
                repository = extension.repository.get()
            )
        }
    }
}
