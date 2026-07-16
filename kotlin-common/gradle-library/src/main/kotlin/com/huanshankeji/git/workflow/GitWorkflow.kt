package com.huanshankeji.git.workflow

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.git.gitCurrentBranch
import com.huanshankeji.isStandardReleaseVersion
import com.huanshankeji.versionStringProvider
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory

/**
 * Not recommended under the current convention: set the release version explicitly on the
 * release branch instead of detecting it from Git.
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
 * `v$version` for a standard release version; otherwise [gitCommitHash] (HEAD).
 * A dirty working tree still resolves to HEAD, so Dokka source links may not match
 * uncommitted local edits. This makes debugging easier.
 */
fun Project.conventionalGitCommitHashOrTag(): Provider<String> =
    versionStringProvider().flatMap { version ->
        if (isStandardReleaseVersion(version)) provider { "v$version" }
        else providers.gitCommitHash()
    }
