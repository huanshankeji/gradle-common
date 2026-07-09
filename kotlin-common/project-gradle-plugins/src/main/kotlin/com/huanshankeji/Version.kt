package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.versionStringProvider(): Provider<String> =
    provider { version.toString() }


fun isSnapshotVersion(version: String): Boolean =
    version.endsWith("SNAPSHOT")

fun Project.isSnapshotVersion(): Boolean =
    isSnapshotVersion(version.toString())
