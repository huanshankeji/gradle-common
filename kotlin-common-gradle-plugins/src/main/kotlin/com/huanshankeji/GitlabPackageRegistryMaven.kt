package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials

const val GITLAB_HOST = "gitlab.com"

context(project: Project)
fun MavenArtifactRepository.gitlabMavenRepositorySetUrlAndCredentials(nameArg: String, urlArg: String) {
    url = project.uri(urlArg)
    name = nameArg
    credentials(HttpHeaderCredentials::class) {
        name = "Private-Token"
        value = gitlabMavenPrivateToken { project.findProperty(it) as String? }
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}

@Deprecated(
    "Use repositories { maven { with(project) { gitlabMavenRepositorySetUrlAndCredentials(name, url) } } } instead",
    ReplaceWith("repositories { maven { with(project) { gitlabMavenRepositorySetUrlAndCredentials(nameArg, urlArg) } } }")
)
fun Project.gitlabMavenRepository(repositoryHandler: RepositoryHandler, nameArg: String = "GitLab", urlArg: String) =
    repositoryHandler.maven {
        with(this@gitlabMavenRepository) {
            gitlabMavenRepositorySetUrlAndCredentials(nameArg, urlArg)
        }
    }

fun Project.gitlabProjectLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    projectIdOrProjectPath: String,
) =
    gitlabMavenRepository(
        repositoryHandler,
        name,
        "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven",
    )

fun Project.gitlabGroupLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String = GITLAB_HOST,
    groupId: String,
) =
    gitlabMavenRepository(repositoryHandler, name, "https://$host/api/v4/groups/$groupId/-/packages/maven")

fun Project.gitlabInstanceLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab",
    host: String,
) =
    gitlabMavenRepository(repositoryHandler, name, "https://$host/api/v4/packages/maven")
