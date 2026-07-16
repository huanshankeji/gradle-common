package com.huanshankeji.gitversioning.opensourceconvention

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.STANDARD_RELEASE_VERSION_REGEX
import com.huanshankeji.gitversioning.STANDARD_DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [devMavenRepository]: `*-dev-commit-*`; Maven Central: releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 *
 * Uses separate [exclusiveContent] blocks with disjoint [includeVersionByRegex] filters so Gradle’s
 * OR’d includes cannot widen version acceptance across repositories (e.g. Maven Central accepting
 * `*-dev-commit-*`).
 *
 * @see conventionMavenRepositories
 */
@GradleCommonExperimentalApi
fun RepositoryHandler.openSourceConventionMavenRepositories(
    devMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) {
    val mavenLocalRepository = mavenLocal()
    val remoteRepository = devMavenRepository {}
    val mavenCentralRepository = mavenCentral()

    exclusiveContent {
        forRepositories(mavenLocalRepository)
        filter { exclusiveContentFilterConfig(SNAPSHOT_VERSION_REGEX) }
    }
    // `*-dev-commit-*` from both `mavenLocal` and `devMavenRepository` in order.
    exclusiveContent {
        forRepositories(mavenLocalRepository, remoteRepository)
        filter { exclusiveContentFilterConfig(STANDARD_DEV_COMMIT_VERSION_REGEX) }
    }
    exclusiveContent {
        forRepositories(mavenCentralRepository)
        filter { exclusiveContentFilterConfig(STANDARD_RELEASE_VERSION_REGEX) }
    }
}

@GradleCommonExperimentalApi
fun RepositoryHandler.openSourceConventionMavenRepositories(
    devMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    groupRegex: String,
    moduleRegex: String,
) =
    openSourceConventionMavenRepositories(devMavenRepository) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
