package com.huanshankeji.team.maven

import com.huanshankeji.contentExcludeHuanshankejiNonStableVersions
import com.huanshankeji.contentIncludeHuanshankejiDevCommitVersions
import com.huanshankeji.contentIncludeHuanshankejiDirtyAndLegacySnapshots
import com.huanshankeji.githubPackagesMavenRegistrySetUrlAndCredentials
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven

/**
 * Configures Maven local (first) and GitHub Packages for [HUANSHANKEJI_MAVEN_GROUP] artifacts.
 * Intended for public open-source repos that must not be aware of internal project names.
 *
 * Call [configureMavenCentralExcludeHuanshankejiNonStable] on an existing `mavenCentral()` repository.
 */
context(project: Project)
fun RepositoryHandler.configurePublicHuanshankejiArtifactRepositories(
    githubPackageRepositoryNames: List<String> = emptyList(),
    owner: String = HUANSHANKEJI_IN_LOWERCASE,
) {
    mavenLocal {
        content {
            contentIncludeHuanshankejiDevCommitVersions()
            contentIncludeHuanshankejiDirtyAndLegacySnapshots()
        }
    }
    for (repositoryName in githubPackageRepositoryNames) {
        maven {
            name = "GitHubPackages-$repositoryName"
            githubPackagesMavenRegistrySetUrlAndCredentials(owner, repositoryName)
            content {
                contentIncludeHuanshankejiDevCommitVersions()
            }
        }
    }
}

fun MavenArtifactRepository.configureMavenCentralExcludeHuanshankejiNonStable() {
    contentExcludeHuanshankejiNonStableVersions()
}
