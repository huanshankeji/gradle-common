package com.huanshankeji.team.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.gitlabpackageregistry.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
        GITLAB_COM_HOST, projectId, groupRegex, moduleRegex, versionRegexes
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectId, groupRegex, moduleRegex, versionRegexes
        )
    }

context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String,
    group: String,
    projectName: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
        projectId, Regex.escape(group), leadingProjectNameModuleRegex(projectName), versionRegexes
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectId: String,
    group: String,
    projectName: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectId, group, projectName, versionRegexes
        )
    }
