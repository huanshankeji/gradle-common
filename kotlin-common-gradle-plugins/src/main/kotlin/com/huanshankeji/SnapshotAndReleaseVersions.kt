package com.huanshankeji

import org.gradle.api.Project

fun Project.isSnapshotVersion() =
    version.toString().endsWith("SNAPSHOT")

fun isMavenCentralSigningVersion(version: String): Boolean =
    !version.endsWith("SNAPSHOT") &&
        !version.contains("-dev-commit-") &&
        !version.endsWith("-dirty-SNAPSHOT")
