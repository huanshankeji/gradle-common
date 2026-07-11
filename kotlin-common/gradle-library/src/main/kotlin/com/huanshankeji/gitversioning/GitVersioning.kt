package com.huanshankeji.gitversioning

import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.git.isGitWorkingTreeDirty
import com.huanshankeji.git.workflow.isReleaseBranch
import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun devCommitVersionString(baseVersion: String, commitHash: String, dirty: Boolean): String =
    if (dirty) "$baseVersion-dev-commit-$commitHash-dirty-SNAPSHOT"
    else "$baseVersion-dev-commit-$commitHash"

fun Project.devCommitVersionProvider(baseVersion: String): Provider<String> =
    gitCommitHash().zip(isGitWorkingTreeDirty()) { hash, dirty ->
        devCommitVersionString(baseVersion, hash, dirty)
    }

/**
 * Returns [baseVersion] on [releaseBranch]; otherwise a `*-dev-commit-*` version from Git.
 * Override with the Gradle property `com.huanshankeji.forceReleaseVersion=true` when needed.
 */
fun Project.projectVersionFromGitProvider(
    baseVersion: String,
    releaseBranch: String = "release",
): Provider<String> {
    val forceRelease = providers.gradleProperty("com.huanshankeji.forceReleaseVersion")
        .map { it.toBoolean() }
        .orElse(false)
    return forceRelease.flatMap { forced ->
        if (forced) providers.provider { baseVersion }
        else isReleaseBranch(releaseBranch).flatMap { release ->
            if (release) providers.provider { baseVersion }
            else devCommitVersionProvider(baseVersion)
        }
    }
}

fun isDirtyDevCommitVersion(version: String): Boolean =
    version.endsWith("-dirty-SNAPSHOT")

fun Project.isDirtyDevCommitVersion(): Boolean =
    isDirtyDevCommitVersion(version.toString())

fun isDevCommitVersion(version: String): Boolean =
    version.contains("-dev-commit-")

fun Project.isDevCommitVersion(): Boolean =
    isDevCommitVersion(version.toString())
