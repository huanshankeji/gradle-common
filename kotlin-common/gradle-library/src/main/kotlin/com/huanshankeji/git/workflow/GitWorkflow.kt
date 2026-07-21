package com.huanshankeji.git.workflow

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.git.gitCurrentBranch
import com.huanshankeji.gitversioning.devCommitOrReleaseVersionProvider
import com.huanshankeji.isStandardReleaseVersion
import com.huanshankeji.versionStringProvider
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory

/**
 * This API is currently not recommended under our convention.
 * Prefer an explicit `isRelease` flag with [devCommitOrReleaseVersionProvider] over detecting the Git branch for project versioning.
 */
@GradleCommonExperimentalApi
fun ProviderFactory.isReleaseBranch(releaseBranch: String = "release"): Provider<Boolean> =
    gitCurrentBranch().map { branch ->
        // gitCurrentBranch() uses `git rev-parse --abbrev-ref HEAD`, which returns the short
        // branch name (e.g. "release"), never the full ref (e.g. "refs/heads/release").
        // Full refs appear in CI context (GITHUB_REF / github.ref), not from this Git helper.
        // branch == releaseBranch || branch == "refs/heads/$releaseBranch"
        branch == releaseBranch
    }

/**
 * A Git ref for source links: the release tag `v$version` for a standard release version,
 * otherwise [gitCommitHash] (HEAD).
 * A dirty working tree still resolves to HEAD, so Dokka source links may not match
 * uncommitted local edits. Made this way to make debugging easier.
 */
fun Project.conventionalGitRef(): Provider<String> =
    versionStringProvider().flatMap { version ->
        if (isStandardReleaseVersion(version)) provider { "v$version" }
        else providers.gitCommitHash()
    }
