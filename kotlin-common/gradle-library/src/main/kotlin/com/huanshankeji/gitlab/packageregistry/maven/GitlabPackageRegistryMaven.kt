package com.huanshankeji.gitlab.packageregistry.maven

import com.huanshankeji.GradleCommonExperimentalApi
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
    name: String,
    urlProvider: Provider<String>,
) {
    setUrl(urlProvider.map(uri))
    this.name = name
    credentials(HttpHeaderCredentials::class) {
        this.name = "Private-Token"
        value = providers.gitlabPackageRegistryPrivateToken()
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}


const val GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME = "GitLabPackageRegistry"

context(_: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    urlProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
) =
    maven {
        gitlabPackageRegistrySetUrlAndCredentials(name, urlProvider)
        extraAction()
    }


const val GITLAB_COM_HOST = "gitlab.com"

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#project-level-maven-endpoint (link outdated)
// only the project's ID can be used for publishing. TODO Check if only the project's ID can be used for consumption too. If so, move this comment into a KDoc and rename the parameter to `projectId`.
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    projectIdOrProjectPathProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        name,
        hostProvider.zip(projectIdOrProjectPathProvider) { host, projectIdOrProjectPath ->
            "https://$host/api/v4/projects/$projectIdOrProjectPath/packages/maven"
        },
        extraAction
    )

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    groupIdProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        name,
        hostProvider.zip(groupIdProvider) { host, groupId ->
            "https://$host/api/v4/groups/$groupId/-/packages/maven"
        },
        extraAction
    )

// see: https://docs.gitlab.com/ee/user/packages/maven_repository/#group-level-maven-endpoint (link outdated)
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    name: String = GITLAB_PACKAGE_REGISTRY_DEFAULT_REPOSITORY_NAME,
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        name,
        hostProvider.map { host -> "https://$host/api/v4/packages/maven" },
        extraAction
    )
