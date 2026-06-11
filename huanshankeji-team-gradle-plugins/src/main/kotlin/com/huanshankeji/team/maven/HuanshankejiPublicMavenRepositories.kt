package com.huanshankeji.team.maven

import com.huanshankeji.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.DIRTY_DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.HUANSHANKEJI_MAVEN_GROUP
import com.huanshankeji.LEGACY_SNAPSHOT_VERSION_REGEX
import com.huanshankeji.contentExcludeHuanshankejiNonStableVersions
import com.huanshankeji.githubMavenPassword
import com.huanshankeji.githubMavenUsername
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.maven
import java.net.URI

/**
 * Configures Maven local (first) and GitHub Packages for [HUANSHANKEJI_MAVEN_GROUP] artifacts.
 * Intended for public open-source repos that must not be aware of internal project names.
 *
 * Call [configureMavenCentralExcludeHuanshankejiNonStable] on an existing `mavenCentral()` repository.
 */
fun RepositoryHandler.configurePublicHuanshankejiArtifactRepositories(
    property: (String) -> String?,
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
                username = githubMavenUsername(property)
                password = githubMavenPassword(property)
            }
            content {
                includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DEV_COMMIT_VERSION_REGEX)
            }
        }
    }
}

fun RepositoryHandler.configurePublicHuanshankejiArtifactRepositories(
    settings: Settings,
    githubPackageRepositoryNames: List<String> = emptyList(),
    owner: String = HUANSHANKEJI_IN_LOWERCASE,
) = configurePublicHuanshankejiArtifactRepositories(
    property = { settings.providers.gradleProperty(it).orNull },
    githubPackageRepositoryNames = githubPackageRepositoryNames,
    owner = owner,
)

fun MavenArtifactRepository.configureMavenCentralExcludeHuanshankejiNonStable() {
    contentExcludeHuanshankejiNonStableVersions()
}
