@file:Suppress("DEPRECATION")

package com.huanshankeji

import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryGroupLevelEndpointMavenRepository
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryInstanceLevelEndpointMavenRepository
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryMavenRepository
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler

// All APIs in this file are deprecated and this file can be removed directly in the future.

private const val GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE =
    "Use the new APIs in `com.huanshankeji.gitlab.packageregistry.maven` instead."

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabMavenRepository(repositoryHandler: RepositoryHandler, nameArg: String = "GitLab", urlArg: String) =
    context(providers, ::uri) {
        repositoryHandler.gitlabPackageRegistryMavenRepository(nameArg, provider { urlArg })
    }

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
const val GITLAB_HOST = "gitlab.com"

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabProjectLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    projectIdOrProjectPath: String,
) =
    context(providers, ::uri) {
        repositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
            name, provider { host }, provider { projectIdOrProjectPath }
        )
    }

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabGroupLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    groupId: String,
) =
    context(providers, ::uri) {
        repositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
            name, provider { host }, provider { groupId }
        )
    }

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabInstanceLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String,
) =
    context(providers, ::uri) {
        repositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(name, provider { host })
    }