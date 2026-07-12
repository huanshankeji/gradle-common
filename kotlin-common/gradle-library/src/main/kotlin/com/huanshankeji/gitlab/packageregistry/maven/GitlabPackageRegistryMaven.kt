package com.huanshankeji.gitlab.packageregistry.maven

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials
import java.net.URI

@GradleCommonExperimentalApi
fun ProviderFactory.gitlabPackageRegistryPrivateToken(): String? =
    gradleProperty("gitLabPrivateToken").getOrNull()

@GradleCommonExperimentalApi
context(providers: ProviderFactory, uri: (path: Any) -> URI)
fun MavenArtifactRepository.gitlabPackageRegistrySetUrlAndCredentials(
    urlProvider: Provider<String>,
) {
    setUrl(urlProvider.map(uri))
    credentials(HttpHeaderCredentials::class) {
        this.name = "Private-Token"
        value = providers.gitlabPackageRegistryPrivateToken()
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}

const val GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME = "GitLabPackageRegistry"

/**
 * Adds a GitLab package registry Maven repository.
 *
 * Pass [extraAction] for additional configuration, such as overriding [MavenArtifactRepository.name].
 */
context(_: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryMavenRepository(
    urlProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
) =
    maven {
        name = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME
        gitlabPackageRegistrySetUrlAndCredentials(urlProvider)
        extraAction()
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryMavenRepository(
    urlProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryMavenRepository(urlProvider, extraAction)
    }


const val GITLAB_COM_HOST = "gitlab.com"

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#project-level-maven-endpoint (link outdated)
// only the project's ID can be used for publishing. TODO Check if only the project's ID can be used for consumption too. If so, move this comment into a KDoc and rename the parameter to `projectId`.
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    projectIdOrProjectPathProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        hostProvider.zip(projectIdOrProjectPathProvider) { host, projectIdOrProjectPath ->
            "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven"
        },
        extraAction
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    projectIdOrProjectPathProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
            hostProvider, projectIdOrProjectPathProvider, extraAction
        )
    }

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    groupIdProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        hostProvider.zip(groupIdProvider) { host, groupId ->
            "https://$host/api/v4/groups/$groupId/-/packages/maven"
        },
        extraAction
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    groupIdProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
            hostProvider, groupIdProvider, extraAction
        )
    }

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        hostProvider.map { host -> "https://$host/api/v4/packages/maven" },
        extraAction
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(hostProvider, extraAction)
    }
