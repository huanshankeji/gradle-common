package com.huanshankeji.team.maven

import com.huanshankeji.github.packages.githubPackagesMavenRegistrySetUrlAndCredentials
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings

/**
 * Composable DSL for public OSS repos. No repositories are added unless explicitly configured
 * in `settings.gradle.kts` after applying the settings plugin.
 */
open class PublicOpenSourceDependencyRepositoriesExtension {
    internal lateinit var repositories: RepositoryHandler
    internal lateinit var settings: Settings

    fun huanshankejiMavenLocal() {
        repositories.mavenLocal {
            content {
                contentIncludeHuanshankejiDevCommitVersions()
                contentIncludeHuanshankejiDirtyAndLegacySnapshots()
            }
        }
    }

    fun githubPackages(vararg repositoryNames: String, owner: String = HUANSHANKEJI_IN_LOWERCASE) {
        for (repositoryName in repositoryNames) {
            repositories.maven {
                name = "GitHubPackages-$repositoryName"
                with(settings) {
                    githubPackagesMavenRegistrySetUrlAndCredentials(owner, repositoryName)
                }
                content {
                    contentIncludeHuanshankejiDevCommitVersions()
                }
            }
        }
    }

    fun mavenCentral(configure: MavenArtifactRepository.() -> Unit = {}) {
        repositories.mavenCentral(configure)
    }

    fun mavenCentralExcludingHuanshankejiNonStable() {
        mavenCentral {
            contentExcludeHuanshankejiNonStableVersions()
        }
    }

    fun google() {
        repositories.google()
    }
}
