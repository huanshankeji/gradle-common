package com.huanshankeji.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI


/**
 * Exclusive mavenLocal + GitLab project endpoint for modules filtered by [filterConfig].
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitLab package registry: clean `*-dev-commit-*` + releases.
 * No Maven Central.
 *
 * @param projectIdOrProjectPath See [RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository].
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    conventionMavenRepositories(
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
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
    filterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
            host, projectIdOrProjectPath, versionRegexes, filterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
        host, projectIdOrProjectPath, versionRegexes
    ) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
            host, projectIdOrProjectPath, groupRegex, moduleRegex, versionRegexes
        )
    }
