package com.huanshankeji.gitversioning

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [remoteMavenRepository]: `*-dev-commit-*` + releases.
 */
fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    exclusiveContentFilter: InclusiveRepositoryContentDescriptor.() -> Unit,
) {
    exclusiveContent {
        forRepository {
            mavenLocal {
                content {
                    includeVersionByRegex(".+", ".+", SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX)
                }
            }
        }
        forRepository {
            remoteMavenRepository {
                content {
                    includeVersionByRegex(".+", ".+", DEV_COMMIT_AND_RELEASE_VERSION_REGEX)
                }
            }
        }
        filter(exclusiveContentFilter)
    }
}
