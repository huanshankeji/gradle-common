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
    repositoryHandler.gitlabPackageRegistryMavenRepository(nameArg, urlArg)

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
const val GITLAB_HOST = "gitlab.com"

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabProjectLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    projectIdOrProjectPath: String,
) =
    repositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository(name, host, projectIdOrProjectPath)

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabGroupLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    groupId: String,
) =
    repositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository(name, host, groupId)

@Deprecated(GITLAB_PACKAGE_REGISTRY_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.gitlabInstanceLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String,
) =
    repositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(name, host)
