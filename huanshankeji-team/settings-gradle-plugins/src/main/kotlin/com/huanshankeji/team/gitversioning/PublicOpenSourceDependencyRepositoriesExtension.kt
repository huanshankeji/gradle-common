package com.huanshankeji.team.gitversioning

import com.huanshankeji.github.packages.maven.githubPackagesSetUrlAndCredentials
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Composable DSL for public OSS repos. No repositories are added unless explicitly configured
 * in `settings.gradle.kts` after applying the settings plugin.
 *
 * Canonical call order: [mavenCentralExcludingHuanshankeji], [google], [githubPackages].
 */
open class PublicOpenSourceDependencyRepositoriesExtension {
    internal lateinit var repositories: RepositoryHandler
    internal lateinit var providers: ProviderFactory
    internal lateinit var uri: (Any) -> URI

    fun mavenCentralExcludingHuanshankeji() {
        repositories.mavenCentral {
            content {
                excludeGroupAndSubgroups(HUANSHANKEJI_MAVEN_GROUP)
            }
        }
    }

    fun google() {
        repositories.google {
            mavenContent {
                includeGoogleMavenGroups()
            }
        }
    }

    fun mavenCentral(configure: MavenArtifactRepository.() -> Unit = {}) {
        repositories.mavenCentral(configure)
    }

    /**
     * One exclusive block per GitHub repository name.
     * mavenLocal: SNAPSHOT + dev-commit; GitHub: dev-commit; Maven Central: releases.
     * Module convention: `com.huanshankeji` + `$repositoryName.*`.
     */
    fun githubPackages(vararg repositoryNames: String, owner: String = HUANSHANKEJI_IN_LOWERCASE) {
        for (repositoryName in repositoryNames) {
            val moduleRegex = "$repositoryName.*"
            context(providers, uri) {
                // Factory overload of forRepository: lambda must return ArtifactRepository.
                repositories.exclusiveContent {
                    forRepository {
                        repositories.mavenLocal {
                            content {
                                includeSnapshotAndDevCommitVersions(HUANSHANKEJI_MAVEN_GROUP_REGEX, moduleRegex)
                            }
                        }
                    }
                    forRepository {
                        repositories.maven {
                            githubPackagesSetUrlAndCredentials(
                                providers.provider { owner },
                                providers.provider { repositoryName },
                            )
                            content {
                                includeDevCommitVersions(HUANSHANKEJI_MAVEN_GROUP_REGEX, moduleRegex)
                            }
                        }
                    }
                    forRepository {
                        repositories.mavenCentral {
                            content {
                                includeReleaseVersions(HUANSHANKEJI_MAVEN_GROUP_REGEX, moduleRegex)
                            }
                        }
                    }
                    filter {
                        includeModuleByRegex(HUANSHANKEJI_MAVEN_GROUP_REGEX, moduleRegex)
                    }
                }
            }
        }
    }
}
