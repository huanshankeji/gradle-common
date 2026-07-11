package com.huanshankeji.team.gitversioning.gitlabpackageregistry

import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitversioning.gitlabpackageregistry.gitlabPackageRegistryProjectEndpointConventionMavenRepositories
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.huanshankejiGitLabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, moduleRegex: String
) =
    gitlabPackageRegistryProjectEndpointConventionMavenRepositories(
        HUANSHANKEJI_IN_LOWERCASE, GITLAB_COM_HOST, projectId, groupRegex, moduleRegex
    )

context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.defaultHuanshankejiGitLabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String, groupRegex: String, projectName: String
) =
    huanshankejiGitLabPackageRegistryProjectEndpointConventionMavenRepositories(
        projectId, groupRegex, leadingProjectNameModuleRegex(projectName)
    )
