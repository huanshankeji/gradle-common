package com.huanshankeji.gitversioning.opensourceconvention.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.opensourceconvention.openSourceConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitLab: `*-dev-commit-*`; Maven Central: releases.
 *
 * @param projectIdOrProjectPath See [gitlabPackageRegistryProjectLevelEndpointMavenRepository].
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    openSourceConventionMavenRepositories(
        {
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                providers.provider { host },
                providers.provider { projectIdOrProjectPath },
            )
        },
        versionRegexes,
        filterConfig,
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
            host, projectIdOrProjectPath, versionRegexes, filterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
        host, projectIdOrProjectPath, versionRegexes
    ) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
            host, projectIdOrProjectPath, groupRegex, moduleRegex, versionRegexes
        )
    }
