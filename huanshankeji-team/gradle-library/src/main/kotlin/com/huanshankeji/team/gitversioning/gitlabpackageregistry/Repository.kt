package com.huanshankeji.team.gitversioning.gitlabpackageregistry

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistryProjectLevelEndpointMavenRepository
import com.huanshankeji.gitversioning.ConventionVersionRegexes
import com.huanshankeji.gitversioning.gitlabpackageregistry.gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * @param projectIdOrProjectPath See [RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository].
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    gitlabPackageRegistryProjectLevelEndpointConventionMavenRepositories(
        projectIdOrProjectPath = projectIdOrProjectPath,
        groupRegex = groupRegex,
        moduleRegex = moduleRegex,
        versionRegexes = versionRegexes,
    )

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectIdOrProjectPath: String,
    groupRegex: String,
    moduleRegex: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectIdOrProjectPath, groupRegex, moduleRegex, versionRegexes
        )
    }

/**
 * @param projectIdOrProjectPath See [RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository].
 * @param projectName serves as the prefix for the default [leadingProjectNameModuleRegex] `moduleRegex`.
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectIdOrProjectPath: String,
    projectName: String,
    group: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    huanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
        projectIdOrProjectPath, Regex.escape(group), leadingProjectNameModuleRegex(projectName), versionRegexes
    )

/**
 * @param projectIdOrProjectPath See [RepositoryHandler.gitlabPackageRegistryProjectLevelEndpointMavenRepository].
 * @param projectName serves as the prefix for the default [leadingProjectNameModuleRegex] `moduleRegex`.
 */
@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
    projectIdOrProjectPath: String,
    projectName: String,
    group: String,
    versionRegexes: ConventionVersionRegexes = ConventionVersionRegexes(),
) =
    context(providers, uri) {
        repositories.defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories(
            projectIdOrProjectPath, projectName, group, versionRegexes
        )
    }
