package com.huanshankeji.gitversioning.opensourceconvention

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.RELEASE_VERSION_REGEX
import com.huanshankeji.gitversioning.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.conventionMavenRepositories
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [devCommitMavenRepository]: `*-dev-commit-*`; Maven Central: releases.
 * This function can be used for both single-project repositories and multi-project repositories.
 * @see conventionMavenRepositories
 */
@GradleCommonExperimentalApi
fun RepositoryHandler.openSourceConventionMavenRepositories(
    devCommitMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    exclusiveContentFilterConfig: InclusiveRepositoryContentDescriptor.() -> Unit,
) {
    // Factory overload of forRepository: lambda must return ArtifactRepository.
    exclusiveContent {
        // Note that `*-dev-commit-*` versions are resolved from both `mavenLocal` and `devCommitMavenRepository` in order.
        forRepository {
            mavenLocal {
                content {
                    //includeVersionByRegex(groupRegex, moduleRegex, SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX)
                    includeVersionByRegex(".+", ".+", SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX)
                }
            }
        }
        forRepository {
            devCommitMavenRepository {
                content {
                    //includeVersionByRegex(groupRegex, moduleRegex, DEV_COMMIT_VERSION_REGEX)
                    includeVersionByRegex(".+", ".+", DEV_COMMIT_VERSION_REGEX)
                }
            }
        }
        forRepository {
            mavenCentral {
                content {
                    //includeVersionByRegex(groupRegex, moduleRegex, RELEASE_VERSION_REGEX)
                    includeVersionByRegex(".+", ".+", RELEASE_VERSION_REGEX)
                }
            }
        }
        filter(exclusiveContentFilterConfig)
    }
}
