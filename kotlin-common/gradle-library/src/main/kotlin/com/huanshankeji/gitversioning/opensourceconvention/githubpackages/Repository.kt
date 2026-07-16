package com.huanshankeji.gitversioning.opensourceconvention.githubpackages

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.github.packages.maven.githubPackagesMavenRegistry
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.opensourceconvention.openSourceConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitHub: `*-dev-commit-*`; Maven Central: releases.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    openSourceConventionMavenRepositories(
        { extraAction ->
            githubPackagesMavenRegistry(
                providers.provider { githubOwner },
                providers.provider { githubRepository },
                extraAction
            )
        },
        versionRegexes,
        exclusiveContentFilterConfig,
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    context(providers, uri) {
        repositories.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
            githubOwner, githubRepository, versionRegexes, exclusiveContentFilterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
        githubOwner, githubRepository, versionRegexes
    ) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
            githubOwner, githubRepository, groupRegex, moduleRegex, versionRegexes
        )
    }


// made private now as these APIs don't really simplify configuration now

private data class RepositoryNameAndModuleRegex(val githubRepositoryName: String, val moduleRegex: String) {
    companion object {
        fun Default(githubRepositoryName: String) =
            RepositoryNameAndModuleRegex(githubRepositoryName, leadingProjectNameModuleRegex(githubRepositoryName))
    }
}

context(_: ProviderFactory, _: (path: Any) -> URI)
private fun RepositoryHandler.githubPackagesMultiProjectOpenSourceConventionMavenRepositories(
    groupRegex: String,
    githubOwner: String,
    repositoryNameAndModuleRegexes: List<RepositoryNameAndModuleRegex>,
) {
    for ((moduleRegex, repository) in repositoryNameAndModuleRegexes)
        githubPackagesSingleProjectOpenSourceConventionMavenRepositories(groupRegex, moduleRegex, githubOwner, repository)
}
