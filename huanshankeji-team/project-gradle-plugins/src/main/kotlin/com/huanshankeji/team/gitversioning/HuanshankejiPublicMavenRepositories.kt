package com.huanshankeji.team.gitversioning

import com.huanshankeji.github.packages.maven.githubPackagesMavenPassword
import com.huanshankeji.github.packages.maven.githubPackagesMavenUsername
import com.huanshankeji.gitversioning.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

/**
 * Legacy project-level helper. Prefer the settings DSL
 * (`publicOpenSourceDependencyRepositories` / `huanshankejiMavenRepositories`).
 */
@Deprecated("Use publicOpenSourceDependencyRepositories / huanshankejiMavenRepositories settings DSL")
context(project: Project)
fun RepositoryHandler.configurePublicHuanshankejiArtifactRepositories(
    githubPackageRepositoryNames: List<String> = emptyList(),
    owner: String = HUANSHANKEJI_IN_LOWERCASE,
) {
    mavenLocal {
        content {
            includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DEV_COMMIT_VERSION_REGEX)
            includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", SNAPSHOT_VERSION_REGEX)
        }
    }
    for (repositoryName in githubPackageRepositoryNames) {
        maven {
            name = "GitHubPackages-$repositoryName"
            url = URI("https://maven.pkg.github.com/$owner/$repositoryName")
            credentials {
                with(project.providers) {
                    username = githubPackagesMavenUsername()
                    password = githubPackagesMavenPassword()
                }
            }
            content {
                includeVersionByRegex(HUANSHANKEJI_MAVEN_GROUP, ".*", DEV_COMMIT_VERSION_REGEX)
            }
        }
    }
}

@Deprecated("Use mavenCentralExcludingHuanshankeji in the settings DSL", ReplaceWith("content { excludeGroupAndSubgroups(HUANSHANKEJI_MAVEN_GROUP) }"))
fun MavenArtifactRepository.configureMavenCentralExcludeHuanshankejiNonStable() {
    content {
        excludeGroupAndSubgroups(HUANSHANKEJI_MAVEN_GROUP)
    }
}
