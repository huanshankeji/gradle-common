package com.huanshankeji.gitversioning

import com.huanshankeji.GradleCommonExperimentalApi
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Most general exclusive-content partitioning by version kind.
 * Pass the exact repository sets for SNAPSHOT, `*-dev-commit-*`, and release
 * (e.g. omit Maven local from [devCommitMavenRepositories] if desired).
 */
@GradleCommonExperimentalApi
fun RepositoryHandler.conventionMavenRepositories(
    snapshotMavenRepositories: List<MavenArtifactRepository>,
    devCommitMavenRepositories: List<MavenArtifactRepository>,
    releaseMavenRepositories: List<MavenArtifactRepository>,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) {
    exclusiveContent {
        forRepositories(*snapshotMavenRepositories.toTypedArray())
        filter { filterConfig(versionRegexes.snapshotVersionRegex) }
    }
    exclusiveContent {
        forRepositories(*devCommitMavenRepositories.toTypedArray())
        filter { filterConfig(versionRegexes.devCommitVersionRegex) }
    }
    exclusiveContent {
        forRepositories(*releaseMavenRepositories.toTypedArray())
        filter { filterConfig(versionRegexes.releaseVersionRegex) }
    }
}

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [remoteDevCommitMavenRepository]: `*-dev-commit-*`;
 * [releaseMavenRepository]: releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 */
@GradleCommonExperimentalApi
fun RepositoryHandler.conventionMavenRepositories(
    remoteDevCommitMavenRepository: MavenArtifactRepository,
    releaseMavenRepository: MavenArtifactRepository,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) {
    val mavenLocalRepository = mavenLocal()
    conventionMavenRepositories(
        listOf(mavenLocalRepository),
        listOf(mavenLocalRepository, remoteDevCommitMavenRepository),
        listOf(releaseMavenRepository),
        versionRegexes,
        filterConfig,
    )
}

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [remoteMavenRepository]: clean `*-dev-commit-*` + releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 */
fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(/*extraAction: MavenArtifactRepository.() -> Unit*/) -> MavenArtifactRepository,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) {
    val remoteMavenRepository = remoteMavenRepository()
    conventionMavenRepositories(remoteMavenRepository, remoteMavenRepository, versionRegexes, filterConfig)
}

fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(/*extraAction: MavenArtifactRepository.() -> Unit*/) -> MavenArtifactRepository,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    conventionMavenRepositories(remoteMavenRepository, versionRegexes) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
