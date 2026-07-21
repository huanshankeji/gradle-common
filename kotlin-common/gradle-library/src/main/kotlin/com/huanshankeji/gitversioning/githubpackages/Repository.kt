package com.huanshankeji.gitversioning.githubpackages

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.github.packages.maven.githubPackagesMavenRegistry
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Exclusive mavenLocal + GitHub Packages for modules filtered by [filterConfig].
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitHub Packages: clean `*-dev-commit-*` + releases.
 * No Maven Central.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesSingleProjectConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    conventionMavenRepositories(
        {
            githubPackagesMavenRegistry(
                providers.provider { githubOwner },
                providers.provider { githubRepository },
            )
        },
        versionRegexes,
        filterConfig,
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.githubPackagesSingleProjectConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    context(providers, uri) {
        repositories.githubPackagesSingleProjectConventionMavenRepositories(
            githubOwner, githubRepository, versionRegexes, filterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesSingleProjectConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    githubPackagesSingleProjectConventionMavenRepositories(
        githubOwner, githubRepository, versionRegexes
    ) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.githubPackagesSingleProjectConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.githubPackagesSingleProjectConventionMavenRepositories(
            githubOwner, githubRepository, groupRegex, moduleRegex, versionRegexes
        )
    }
