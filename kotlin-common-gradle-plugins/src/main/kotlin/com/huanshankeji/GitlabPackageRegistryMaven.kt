package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials

const val GITLAB_COM_HOST = "gitlab.com"

const val GITLAB_PACKAGE_REGISTRY_REPOSITORY_NAME = "GitLabPackageRegistry"

context(project: Project)
fun MavenArtifactRepository.gitlabPackageRegistryMavenRepositorySetUrlAndCredentials(nameArg: String, urlArg: String) {
    url = project.uri(urlArg)
    name = nameArg
    credentials(HttpHeaderCredentials::class) {
        name = "Private-Token"
        value = project.gitlabPackageRegistryPrivateToken()
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}

context(project: Project)
fun RepositoryHandler.mavenGitlabPackageRegistryRepository(
    nameArg: String = "GitLab",
    urlArg: String,
): MavenArtifactRepository =
    maven {
        gitlabPackageRegistryMavenRepositorySetUrlAndCredentials(nameArg, urlArg)
    }

context(project: Project)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    projectIdOrProjectPath: String,
): MavenArtifactRepository =
    mavenGitlabPackageRegistryRepository(
        name,
        "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven",
    )

context(project: Project)
fun RepositoryHandler.gitlabPackageRegistryGroupLevelMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_REPOSITORY_NAME,
    host: String = GITLAB_COM_HOST,
    groupId: String,
): MavenArtifactRepository =
    mavenGitlabPackageRegistryRepository(name, "https://$host/api/v4/groups/$groupId/-/packages/maven")

context(project: Project)
fun RepositoryHandler.gitlabPackageRegistryInstanceLevelMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_REPOSITORY_NAME,
    host: String,
): MavenArtifactRepository =
    mavenGitlabPackageRegistryRepository(name, "https://$host/api/v4/packages/maven")
