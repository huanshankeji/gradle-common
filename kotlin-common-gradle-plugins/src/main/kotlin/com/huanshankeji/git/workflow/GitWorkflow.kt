package com.huanshankeji.git.workflow

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.git.gitCurrentBranch
import org.gradle.api.Project
import org.gradle.api.provider.Provider

@GradleCommonExperimentalApi
fun Project.isReleaseBranch(releaseBranch: String = "release"): Provider<Boolean> =
    gitCurrentBranch().map { branch ->
        branch == releaseBranch || branch == "refs/heads/$releaseBranch"
    }
