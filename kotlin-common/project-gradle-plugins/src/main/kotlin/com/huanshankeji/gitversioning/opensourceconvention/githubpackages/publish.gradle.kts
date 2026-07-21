package com.huanshankeji.gitversioning.opensourceconvention.githubpackages

import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    id("com.huanshankeji.github.packages.maven.publish")
}

/**
 * Open-source convention: GitHub Packages for non-release / `*-dev-commit-*` versions,
 * Maven Central for release versions. This plugin does **not** enable signing by itself.
 *
 * After applying, call [Extension.signAllPublicationsIfRelease]
 * (required; enforced after project evaluation). That call also gates publish tasks so
 * `publish` uploads to GitHub Packages when `isRelease` is `false`, and to Maven Central when `true`.
 */
abstract class Extension(
    private val project: Project,
) {
    private var signAllPublicationsIfReleaseCalled = false

    /**
     * Must be called once after applying this plugin.
     * When [isRelease] is `true`, enables GPG signing via [MavenPublishBaseExtension.signAllPublications].
     * When `false`, leaves publications unsigned (typical for `*-dev-commit-*` GitHub Packages publishes).
     */
    fun signAllPublicationsIfRelease(isRelease: Boolean) {
        check(!signAllPublicationsIfReleaseCalled) {
            "signAllPublicationsIfRelease must be called at most once"
        }
        signAllPublicationsIfReleaseCalled = true
        if (isRelease) {
            // calling `mavenPublishing` directly here doesn't work
            project.extensions.configure<MavenPublishBaseExtension>("mavenPublishing") {
                signAllPublications()
            }
        }
    }

    internal fun ensureSignAllPublicationsIfReleaseCalled() {
        check(signAllPublicationsIfReleaseCalled) {
            "openSourceConventionGithubPackagesPublish.signAllPublicationsIfRelease(isRelease) must be called " +
                    "when using com.huanshankeji.gitversioning.opensourceconvention.githubpackages.publish"
        }
    }
}

val extension = extensions.create<Extension>("gitVersioningOpenSourceConventionGithubPackagesPublish")

mavenPublishing {
    publishToMavenCentral()
}

// This usage doesn't violate Gradle's best practices. See https://docs.gradle.org/current/userguide/best_practices_general.html#when_afterevaluate_may_still_be_appropriate.
afterEvaluate {
    extension.ensureSignAllPublicationsIfReleaseCalled()
}
