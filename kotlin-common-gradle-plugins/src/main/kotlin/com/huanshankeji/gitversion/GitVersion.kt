package com.huanshankeji.gitversion

import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.gitCommitHash(): Provider<String> =
    providers.exec {
        commandLine("git", "rev-parse", "HEAD")
    }.standardOutput.asText.map { it.trim() }

fun Project.isGitWorkingTreeDirty(): Provider<Boolean> =
    providers.exec {
        commandLine("git", "status", "--porcelain")
    }.standardOutput.asText.map { it.isNotBlank() }

fun Project.gitCurrentBranch(): Provider<String> =
    providers.exec {
        commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
    }.standardOutput.asText.map { it.trim() }

fun Project.isReleaseBranch(releaseBranch: String = "release"): Provider<Boolean> =
    gitCurrentBranch().map { branch ->
        branch == releaseBranch || branch == "refs/heads/$releaseBranch"
    }

fun devCommitVersionString(baseVersion: String, commitHash: String, dirty: Boolean): String =
    if (dirty) "$baseVersion-dev-commit-$commitHash-dirty-SNAPSHOT"
    else "$baseVersion-dev-commit-$commitHash"

fun Project.devCommitVersionProvider(baseVersion: String): Provider<String> =
    gitCommitHash().zip(isGitWorkingTreeDirty()) { hash, dirty ->
        devCommitVersionString(baseVersion, hash, dirty)
    }

/**
 * Returns [baseVersion] on [releaseBranch]; otherwise a dev-commit version from Git.
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

fun Project.isDirtyDevCommitVersion(): Boolean =
    version.toString().endsWith("-dirty-SNAPSHOT")

fun Project.isDevCommitVersion(): Boolean =
    version.toString().contains("-dev-commit-")
