package com.huanshankeji

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.provider.Property
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials

// TODO: use context receivers when it's stable.

const val GITLAB_HOST = "gitlab.com"

fun Project.gitlabMavenRepository(repositoryHandler: RepositoryHandler, nameArg: String = "GitLab", urlArg: String) =
    // adapted from https://docs.gitlab.com/ee/user/packages/maven_repository/#authenticate-with-a-personal-access-token-in-gradle
    repositoryHandler.maven {
        url = uri(urlArg)
        name = nameArg
        credentials(HttpHeaderCredentials::class) {
            name = "Private-Token"
            value = findProperty("gitLabPrivateToken") as String?
        }
        authentication {
            create("header", HttpHeaderAuthentication::class)
        }
    }


// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#project-level-maven-endpoint
// only the project’s ID can be used for publishing.
fun Project.gitlabProjectLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab", host: String = GITLAB_HOST, projectIdOrProjectPath: String
) =
    gitlabMavenRepository(
        repositoryHandler,
        name,
        "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven"
    )

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint
fun Project.gitlabGroupLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab", host: String = GITLAB_HOST, groupId: String
) =
    gitlabMavenRepository(repositoryHandler, name, "https://$host/api/v4/groups/$groupId/-/packages/maven")

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint
fun Project.gitlabInstanceLevelMavenRepository(
    repositoryHandler: RepositoryHandler,
    name: String = "GitLab", host: String
) =
    gitlabMavenRepository(repositoryHandler, name, "https://$host/api/v4/packages/maven")


class GitlabProjectLevelMavenEndpointPublishPlugin : Plugin<Project> {
    interface Extension {
        val host: Property<String>
        val projectId: Property<String>
    }

    override fun apply(project: Project) = project.run {
        pluginManager.apply("maven-publish")

        val extension = extensions.create<Extension>("gitlabPackagesPublish")

        afterEvaluate {
            publishing {
                repositories {
                    gitlabProjectLevelMavenRepository(
                        this,
                        host = extension.host.getOrElse(GITLAB_HOST), projectIdOrProjectPath = extension.projectId.get()
                    )
                }
            }
        }
    }
}
