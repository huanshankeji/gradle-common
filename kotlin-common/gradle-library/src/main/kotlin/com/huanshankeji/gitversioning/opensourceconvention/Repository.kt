package com.huanshankeji.gitversioning.opensourceconvention

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [devMavenRepository]: `*-dev-commit-*`; Maven Central: releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 *
 * @see conventionMavenRepositories
 */
@GradleCommonExperimentalApi
fun RepositoryHandler.openSourceConventionMavenRepositories(
    devMavenRepository: RepositoryHandler.(/*extraAction: MavenArtifactRepository.() -> Unit*/) -> MavenArtifactRepository,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    conventionMavenRepositories(devMavenRepository(), mavenCentral(), versionRegexes, filterConfig)

@GradleCommonExperimentalApi
fun RepositoryHandler.openSourceConventionMavenRepositories(
    devMavenRepository: RepositoryHandler.(/*extraAction: MavenArtifactRepository.() -> Unit*/) -> MavenArtifactRepository,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    openSourceConventionMavenRepositories(devMavenRepository, versionRegexes) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
