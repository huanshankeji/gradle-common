package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.versionStringProvider(): Provider<String> =
    provider { version.toString() }


fun String.isSnapshotVersion(): Boolean =
    endsWith("SNAPSHOT")

fun Project.isSnapshotVersion(): Boolean =
    version.toString().isSnapshotVersion()
