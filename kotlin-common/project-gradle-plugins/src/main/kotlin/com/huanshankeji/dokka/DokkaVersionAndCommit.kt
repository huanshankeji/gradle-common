package com.huanshankeji.dokka

import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.versionStringProvider
import org.gradle.api.Project
import org.gradle.api.provider.Provider

/**
 * Label shown in Dokka HTML: the project version and `git rev-parse HEAD`.
 * `HEAD` is the branch tip on a dev machine and the checked-out commit in CI.
 * A project still on [Project.DEFAULT_VERSION] shows only the commit.
 */
internal fun Project.dokkaVersionAndCommitHash(): Provider<String> =
    versionStringProvider().zip(providers.gitCommitHash()) { version, commitHash ->
        if (version == Project.DEFAULT_VERSION) "commit $commitHash" else "$version (commit $commitHash)"
    }
