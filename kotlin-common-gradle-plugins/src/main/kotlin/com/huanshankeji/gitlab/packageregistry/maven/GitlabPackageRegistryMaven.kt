package com.huanshankeji.gitlab.packageregistry.maven

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.gitlabPackageRegistryPrivateToken
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials

@GradleCommonExperimentalApi
context(project: Project)
fun MavenArtifactRepository.gitlabPackageRegistrySetUrlAndCredentials(name: String, url: String) {
    // adapted from https://docs.gitlab.com/ee/user/packages/maven_repository/#authenticate-with-a-personal-access-token-in-gradle (link outdated as checked)
    this.url = project.uri(url)
    this.name = name
    credentials(HttpHeaderCredentials::class) {
        this.name = "Private-Token"
        value = project.gitlabPackageRegistryPrivateToken()
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}


const val GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME = "GitLabPackageRegistry"

context(_: Project)
fun RepositoryHandler.gitlabPackageRegistryMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    url: String
) =
    maven {
        gitlabPackageRegistrySetUrlAndCredentials(name, url)
    }


const val GITLAB_COM_HOST = "gitlab.com"

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#project-level-maven-endpoint (link outdated)
// only the project’s ID can be used for publishing. TODO Check if only the project’s ID can be used for consumption too. If so, move this comment into a KDoc and rename the parameter to `projectId`.
context(_: Project)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        name,
        "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven",
    )

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(_: Project)
fun RepositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    groupId: String,
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(name, "https://$host/api/v4/groups/$groupId/-/packages/maven")

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(_: Project)
fun RepositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(name, "https://$host/api/v4/packages/maven")
