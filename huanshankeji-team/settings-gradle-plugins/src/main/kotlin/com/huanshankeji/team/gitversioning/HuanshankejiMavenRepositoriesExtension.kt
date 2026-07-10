package com.huanshankeji.team.gitversioning

import com.huanshankeji.gitlab.packageregistry.maven.GITLAB_COM_HOST
import com.huanshankeji.gitlab.packageregistry.maven.gitlabPackageRegistrySetUrlAndCredentials
import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor

/**
 * Internal repos DSL: public OSS surface plus GitLab package registry endpoints.
 *
 * Canonical call order: [mavenCentralExcludingHuanshankeji], [google], [githubPackages],
 * [gitlabProjectEndpoint].
 */
open class HuanshankejiMavenRepositoriesExtension : PublicOpenSourceDependencyRepositoriesExtension() {
    /**
     * Exclusive mavenLocal + GitLab project endpoint for [exclusiveFilter] modules.
     * mavenLocal: SNAPSHOT + dev-commit; GitLab: dev-commit + releases.
     * No Maven Central — internal artifacts are not published there.
     */
    fun gitlabProjectEndpoint(
        projectId: String,
        nameSuffix: String = projectId,
        exclusiveFilter: InclusiveRepositoryContentDescriptor.() -> Unit,
    ) {
        context(providers, uri) {
            repositories.exclusiveContent {
                forRepository {
                    repositories.mavenLocal {
                        content {
                            includeSnapshotAndDevCommitVersions()
                        }
                    }
                }
                forRepository {
                    repositories.maven {
                        gitlabPackageRegistrySetUrlAndCredentials(
                            "GitLab-$nameSuffix",
                            providers.provider { "https://$GITLAB_COM_HOST/api/v4/projects/$projectId/packages/maven" },
                        )
                        content {
                            includeDevCommitAndReleaseVersions()
                        }
                    }
                }
                filter(exclusiveFilter)
            }
        }
    }
}
