package com.huanshankeji.github.packages.maven

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.artifacts.MavenRepositoryHandlerContext
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * GitHub Packages Maven username from Gradle properties `gpr.user` or `gprUser`.
 *
 * [GitHub's Gradle registry docs](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)
 * use [Project.findProperty] for `gpr.user`; we resolve the same property names with
 * [gradleProperty] instead, which reads only build-level Gradle property sources
 * (`gradle.properties`, `-P`, `ORG_GRADLE_PROJECT_*`, …) as
 * [Gradle recommends](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_system_properties).
 */
@GradleCommonExperimentalApi
fun ProviderFactory.githubPackagesMavenUsername(): String? =
    gradleProperty("gpr.user").orElse(gradleProperty("gprUser")).getOrNull()

/**
 * GitHub Packages Maven password from Gradle properties `gpr.key` or `gprKey`.
 *
 * See [githubPackagesMavenUsername] for why [gradleProperty] is used rather than
 * [Project.findProperty].
 */
@GradleCommonExperimentalApi
fun ProviderFactory.githubPackagesMavenPassword(): String? =
    gradleProperty("gpr.key").orElse(gradleProperty("gprKey")).getOrNull()

@GradleCommonExperimentalApi
context(providers: ProviderFactory, uri: (path: Any) -> URI)
fun MavenArtifactRepository.githubPackagesSetUrlAndCredentials(
    ownerProvider: Provider<String>,
    repositoryProvider: Provider<String>,
) {
    setUrl(ownerProvider.zip(repositoryProvider) { owner, repository ->
        uri("https://maven.pkg.github.com/$owner/$repository")
    })
    credentials {
        with(providers) {
            username = githubPackagesMavenUsername()
            password = githubPackagesMavenPassword()
        }
    }
}

const val GITHUB_PACKAGES_DEFAULT_REPOSITORY_NAME = "GitHubPackages"

/**
 * Adds a GitHub packages Maven repository.
 *
 * Pass [extraAction] for additional configuration, such as overriding [MavenArtifactRepository.name].
 */
context(_: ProviderFactory, _: (path: Any) -> URI)
fun RepositoryHandler.githubPackagesMavenRegistry(
    ownerProvider: Provider<String>,
    repositoryProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {}
) =
    maven {
        // Copied and adapted from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry.
        name = GITHUB_PACKAGES_DEFAULT_REPOSITORY_NAME
        githubPackagesSetUrlAndCredentials(ownerProvider, repositoryProvider)
        extraAction()
    }

@GradleCommonExperimentalApi
fun MavenRepositoryHandlerContext.githubPackagesMavenRegistry(
    ownerProvider: Provider<String>,
    repositoryProvider: Provider<String>,
    extraAction: MavenArtifactRepository.() -> Unit = {},
) =
    context(providers, uri) {
        repositories.githubPackagesMavenRegistry(ownerProvider, repositoryProvider, extraAction)
    }
