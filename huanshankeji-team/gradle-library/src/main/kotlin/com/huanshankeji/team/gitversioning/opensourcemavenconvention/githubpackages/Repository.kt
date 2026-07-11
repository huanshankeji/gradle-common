package com.huanshankeji.team.gitversioning.opensourcemavenconvention.githubpackages

import com.huanshankeji.artifacts.leadingProjectNameModuleRegex
import com.huanshankeji.gitversioning.opensourceconvention.githubpackages.githubPackagesSingleProjectOpenSourceConventionMavenRepositories
import com.huanshankeji.team.HUANSHANKEJI_GROUP_REGEX
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * @param projectName serves as both the repository name and the previx for the deefault [leadingProjectNameModuleRegex] [moduleRegex].
 */
context(providers: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.huanshankejiGithubPackagesOpenSourceMavenConventionProjectRepositories(
    projectName: String,
    groupRegex: String = HUANSHANKEJI_GROUP_REGEX,
    moduleRegex: String = leadingProjectNameModuleRegex(projectName)
) =
    githubPackagesSingleProjectOpenSourceConventionMavenRepositories(
        HUANSHANKEJI_IN_LOWERCASE, projectName, groupRegex, moduleRegex
    )
