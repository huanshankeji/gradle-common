package com.huanshankeji.team.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitversioning.gitlabpackageregistry.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, moduleRegex: String
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
        GITLAB_COM_HOST, projectId, groupRegex, moduleRegex
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, moduleRegex: String
) =
    context(providers, uri) {
        repositories.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectId, groupRegex, moduleRegex
        )
    }

@Deprecated("Not actually used in practice now.")
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, projectName: String
) =
    huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
        projectId, groupRegex, leadingProjectNameModuleRegex(projectName)
    )

@Deprecated("Not actually used in practice now.")
@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, projectName: String
) =
    context(providers, uri) {
        repositories.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectId, groupRegex, projectName
        )
    }
