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

/**
 * See https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#custom-http-header.
 * Note that per the official guide the spelling is `gitLab` not `gitlab` in the property name.
 */
@GradleCommonExperimentalApi
fun ProviderFactory.gitlabPackageRegistryPrivateToken(): String? =
    gradleProperty("gitLabPrivateToken").getOrNull()

/**
 * Configures the repository URL and authenticates with a Custom HTTP header
 * (`Private-Token`), as documented at
 * https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#custom-http-header.
 *
 * [Basic HTTP Authentication](https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#basic-http-authentication)
 * is also supported by GitLab, but Custom HTTP header is preferred here
 * (GitLab’s primary Gradle examples, native token headers, OAuth support).
 */
@GradleCommonExperimentalApi
context(providers: ProviderFactory, uri: (path: Any) -> URI)
fun MavenArtifactRepository.gitlabPackageRegistrySetUrlAndCredentials(
    urlProvider: Provider<String>,
) {
    setUrl(urlProvider.map(uri))
    credentials(HttpHeaderCredentials::class) {
        name = "Private-Token"
        value = providers.gitlabPackageRegistryPrivateToken()
    }
    authentication {
        create("header", HttpHeaderAuthentication::class)
    }
}

/**
 * In the guide it's just "GitLab" but we use the full name here to make it more explicit.
 */
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

/**
 * Project-level Maven endpoint:
 * `https://<host>/api/v4/projects/<projectIdOrProjectPath>/packages/maven`
 *
 * See https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#endpoint-urls.
 *
 * @param projectIdOrProjectPathProvider GitLab project identifier for the URL segment.
 * Prefer the numeric [project ID](https://docs.gitlab.com/user/project/working_with_projects/#find-the-project-id)
 * (required for **publishing**; also works for consumption).
 * For **consumption only**, a URL-encoded full project path (e.g. `group%2Fproject`) may work;
 * a bare project name is not sufficient. Publishing with a path fails (GitLab workhorse accepts
 * only a numeric project ID for Maven uploads). (by AI agent, not thoroughly verified)
 */
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

/**
 * @see RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository
 */
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

/**
 * Group-level Maven endpoint:
 * `https://<host>/api/v4/groups/<groupId>/-/packages/maven`
 *
 * See https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#endpoint-urls.
 *
 * @param groupIdProvider Prefer the numeric group ID from the group homepage.
 * A URL-encoded group path may work for consumption on some GitLab versions; current docs
 * document the group ID. (by AI agent, not thoroughly verified)
 */
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

/**
 * @see RepositoryHandler.gitlabPackageRegistryGroupLevelEndpointMavenRepository
 */
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

/**
 * Instance-level Maven endpoint: `https://<host>/api/v4/packages/maven`
 *
 * See https://docs.gitlab.com/user/packages/maven_repository/?tab=gradle#endpoint-urls.
 * Publishing still targets a project-level endpoint; this endpoint is for consumption with
 * the instance naming convention. (by AI agent, not thoroughly verified)
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    extraAction: MavenArtifactRepository.() -> Unit = {}
): MavenArtifactRepository =
    gitlabPackageRegistryMavenRepository(
        hostProvider.map { host -> "https://$host/api/v4/packages/maven" },
        extraAction
    )

/**
 * @see RepositoryHandler.gitlabPackageRegistryInstanceLevelEndpointMavenRepository
 */
@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(
    hostProvider: Provider<String> = providers.provider { GITLAB_COM_HOST },
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.gitlabPackageRegistryInstanceLevelEndpointMavenRepository(hostProvider, extraAction)
    }
