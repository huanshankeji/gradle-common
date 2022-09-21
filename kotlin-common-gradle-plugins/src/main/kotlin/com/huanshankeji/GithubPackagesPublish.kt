package com.huanshankeji

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.create

fun Project.addGithubPackagesMavenRepository(owner: String, repository: String) =
    // Copied and adapted from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry.
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                githubPackagesMavenRegistrySetUrlAndCredentials(this@maven, owner, repository)
            }
        }
    }

// copied and adapted from generated code
fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)

class GithubPackagesPublishPlugin : Plugin<Project> {
    interface Extension {
        val owner: Property<String>
        val repository: Property<String>
    }

    override fun apply(project: Project) = project.run {
        pluginManager.apply("maven-publish")

        val extension = extensions.create<Extension>("githubPackagesPublish")

        afterEvaluate { addGithubPackagesMavenRepository(extension.owner.get(), extension.repository.get()) }
    }
}
