package com.huanshankeji

import com.huanshankeji.github.packages.maven.githubPackagesMavenRegistry
import com.huanshankeji.github.packages.maven.githubPackagesMavenRegistryWithName
import com.huanshankeji.github.packages.maven.githubPackagesSetUrlAndCredentials
import com.huanshankeji.publish.publishing
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.repositories

// All APIs in this file are deprecated and this file can be removed directly in the future.

private const val GITHUB_PACKAGES_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE =
    "Use the new APIs in `com.huanshankeji.github.packages.maven` instead."

@Deprecated(GITHUB_PACKAGES_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.githubPackagesMavenRegistrySetUrlAndCredentials(
    mavenArtifactRepository: MavenArtifactRepository, owner: String, repository: String
) =
    mavenArtifactRepository.githubPackagesSetUrlAndCredentials(owner, repository)

@Deprecated(GITHUB_PACKAGES_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.repositoriesAddGithubPackagesMavenRegistry(owner: String, repository: String) =
    repositories {
        githubPackagesMavenRegistry(owner, repository)
    }

@Deprecated(GITHUB_PACKAGES_MAVEN_REGISTRY_OLD_APIS_DEPRECATION_MESSAGE)
fun Project.publishingRepositoriesAddGithubPackagesMavenRepository(
    name: String = "GitHubPackages",
    owner: String,
    repository: String,
) =
    publishing {
        repositories {
            githubPackagesMavenRegistryWithName(owner, repository, name)
        }
    }
