package com.huanshankeji.gitversioning.githubpackages

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.github.packages.maven.githubPackagesMavenRegistry
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Exclusive mavenLocal + GitHub Packages for modules filtered by [exclusiveContentFilterConfig].
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitHub Packages: `*-dev-commit-*` + releases.
 * No Maven Central.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesConventionMavenRepositories(
    githubOwner: String,
    githubRepository: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) =
    conventionMavenRepositories(
        { extraAction ->
            githubPackagesMavenRegistry(
                providers.provider { githubOwner },
                providers.provider { githubRepository },
                extraAction
            )
        },
        exclusiveContentFilterConfig,
    )

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesConventionMavenRepositories(
    githubOwner: String, githubRepository: String, groupRegex: String, moduleRegex: String
) =
    githubPackagesConventionMavenRepositories(githubOwner, githubRepository) {
        includeModuleByRegex(groupRegex, moduleRegex)
    }
