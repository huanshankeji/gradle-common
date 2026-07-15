package com.huanshankeji.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI


/**
 * Exclusive mavenLocal + GitLab project endpoint for modules filtered by [exclusiveContentFilterConfig].
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitLab package registry: clean `*-dev-commit-*` + releases.
 * No Maven Central.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    conventionMavenRepositories(
        { extraAction ->
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                providers.provider { host },
                providers.provider { projectId },
                extraAction
            )
        }, exclusiveContentFilterConfig
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.(versionRegex: String) -> Unit,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
            host, projectId, exclusiveContentFilterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(host, projectId) { versionRegex ->
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
            host, projectId, groupRegex, moduleRegex
        )
    }
