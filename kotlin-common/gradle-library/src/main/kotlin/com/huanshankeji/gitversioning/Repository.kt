package com.huanshankeji.gitversioning

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [remoteMavenRepository]: clean `*-dev-commit-*` + releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 *
 * Uses separate [exclusiveContent] blocks with disjoint [includeVersionByRegex] filters so Gradle’s
 * OR’d includes cannot widen version acceptance across repositories.
 */
fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    exclusiveContentFilter: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) {
    val mavenLocalRepository = mavenLocal()
    val remoteRepository = remoteMavenRepository {}

    exclusiveContent {
        forRepositories(mavenLocalRepository)
        filter { exclusiveContentFilter(versionRegexes.snapshotVersionRegex) }
    }
    // `*-dev-commit-*` from both `mavenLocal` and `remoteMavenRepository` in order.
    exclusiveContent {
        forRepositories(mavenLocalRepository, remoteRepository)
        filter { exclusiveContentFilter(versionRegexes.devCommitVersionRegex) }
    }
    exclusiveContent {
        forRepositories(remoteRepository)
        filter { exclusiveContentFilter(versionRegexes.releaseVersionRegex) }
    }
}

fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    conventionMavenRepositories(remoteMavenRepository, versionRegexes) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
