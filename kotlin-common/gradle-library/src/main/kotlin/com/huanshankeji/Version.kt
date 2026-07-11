package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.versionStringProvider(): Provider<String> =
    provider { version.toString() }


fun isSnapshotVersion(version: String): Boolean =
    version.endsWith("-SNAPSHOT")

fun Project.isSnapshotVersion(): Boolean =
    isSnapshotVersion(version.toString())

const val SNAPSHOT_VERSION_REGEX = ".*-SNAPSHOT"


/** Semver release, optionally with one of alpha/beta/rc and an extra number, e.g. `1.2.3`, `1.2.3-alpha`, `1.2.3-alpha-1`. */
const val RELEASE_VERSION_REGEX = """\d+\.\d+\.\d+(-(alpha|beta|rc)(-\d+)?)?"""
