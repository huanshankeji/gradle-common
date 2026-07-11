package com.huanshankeji.gitversioning.opensourceconvention.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME
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
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) =
    openSourceConventionMavenRepositories(
        { extraAction ->
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                name,
                providers.provider { host },
                providers.provider { projectId },
                extraAction
            )
        },
        exclusiveContentFilterConfig,
    )

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    gitlabPackageRegistryProjectLevelEndpointOpenSourceConventionMavenRepositories(name, host, projectId) {
        includeModuleByRegex(groupRegex, moduleRegex)
    }
