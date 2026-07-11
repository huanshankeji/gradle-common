package com.huanshankeji.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.provider.ProviderFactory
import java.net.URI


/**
 * Exclusive mavenLocal + GitLab project endpoint for modules filtered by [exclusiveContentFilterConfig].
 * Maven local: SNAPSHOT + `*-dev-commit-*`; GitLab package registry: `*-dev-commit-*` + releases.
 * No Maven Central.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectId: String,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) =
    conventionMavenRepositories(
        { extraAction ->
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                name,
                providers.provider { host },
                providers.provider { projectId },
                extraAction
            )
        }, exclusiveContentFilterConfig
    )

@GradleCommonExperimentalApi
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(name, host, projectId) {
        includeModuleByRegex(groupRegex, moduleRegex)
    }
