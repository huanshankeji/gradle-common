package com.huanshankeji.gitversioning.opensourceconvention.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.opensourceconvention.openSourceConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitLab: `*-dev-commit-*`; Maven Central: releases.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) =
    openSourceConventionMavenRepositories(
        { extraAction ->
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                providers.provider { host },
                providers.provider { projectId },
                extraAction
            )
        },
        exclusiveContentFilterConfig,
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
            host, projectId, exclusiveContentFilterConfig
        )
    }

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(host, projectId) {
        includeModuleByRegex(groupRegex, moduleRegex)
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
            host, projectId, groupRegex, moduleRegex
        )
    }
