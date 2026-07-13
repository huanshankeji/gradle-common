package com.huanshankeji.git.workflow

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.git.gitCurrentBranch
import com.huanshankeji.gitversioning.isDirtyDevCommitVersion
import com.huanshankeji.isReleaseVersion
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

// `String?` doesn't work with this API here.
fun Project.conventionalGitCommitHashOrTag(): Provider<String> =
    versionStringProvider().flatMap { version ->
        if (isDirtyDevCommitVersion()) provider { null } // `null` for dirty versions now.
        else if (isReleaseVersion(version)) provider { "v$version" }
        else providers.gitCommitHash()
    }
