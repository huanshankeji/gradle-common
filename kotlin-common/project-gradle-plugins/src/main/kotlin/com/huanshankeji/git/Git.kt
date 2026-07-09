package com.huanshankeji.git

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
