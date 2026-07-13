package com.huanshankeji.gitversioning

import com.huanshankeji.NON_SNAPSHOT_VERSION_REGEX
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * Maven local: SNAPSHOT + `*-dev-commit-*`; [remoteMavenRepository]: any non-`-SNAPSHOT` version
 * (including clean `*-dev-commit-*` and release coordinates).
 * This function can be used for both single-project repositories and multi-project repositories.
 */
fun RepositoryHandler.conventionMavenRepositories(
    remoteMavenRepository: RepositoryHandler.(extraAction: MavenArtifactRepository.() -> Unit) -> MavenArtifactRepository,
    exclusiveContentFilter: InclusiveRepositoryContentDescriptor.() -> Unit,
) {
    exclusiveContent {
        // Note that `*-dev-commit-*` versions are resolved from both `mavenLocal` and `remoteMavenRepository` in order.
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
                    includeVersionByRegex(".+", ".+", NON_SNAPSHOT_VERSION_REGEX)
                }
            }
        }
        filter(exclusiveContentFilter)
    }
}
