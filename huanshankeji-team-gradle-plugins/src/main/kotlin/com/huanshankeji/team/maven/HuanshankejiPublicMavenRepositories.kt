package com.huanshankeji.team.maven

import com.huanshankeji.*
import com.huanshankeji.github.packages.maven.githubPackagesMavenPassword
import com.huanshankeji.github.packages.maven.githubPackagesMavenUsername
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

/**
 * Configures Maven local (first) and GitHub Packages for [HUANSHANKEJI_MAVEN_GROUP] artifacts.
 * Intended for public open-source repos that must not be aware of internal project names.
 *
 * Call [configureMavenCentralExcludeHuanshankejiNonStable] on an existing `mavenCentral()` repository.
 */
context(project: Project)
fun RepositoryHandler.configurePublicHuanshankejiArtifactRepositories(
    githubPackageRepositoryNames: List<String> = emptyList(),
    owner: String = HUANSHANKEJI_IN_LOWERCASE,
) {
    mavenLocal {
        content {
            includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DEV_COMMIT_VERSION_REGEX)
            includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DIRTY_DEV_COMMIT_VERSION_REGEX)
            includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", LEGACY_SNAPSHOT_VERSION_REGEX)
        }
    }
    for (repositoryName in githubPackageRepositoryNames) {
        maven {
            name = "GitHubPackages-$repositoryName"
            url = URI("https://maven.pkg.github.com/$owner/$repositoryName")
            credentials {
                username = project.githubPackagesMavenUsername()
                password = project.githubPackagesMavenPassword()
            }
            content {
                includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DEV_COMMIT_VERSION_REGEX)
            }
        }
    }
}

fun MavenArtifactRepository.configureMavenCentralExcludeHuanshankejiNonStable() {
    contentExcludeHuanshankejiNonStableVersions()
}
