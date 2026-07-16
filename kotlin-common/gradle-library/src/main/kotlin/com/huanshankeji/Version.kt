package com.huanshankeji

import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.versionStringProvider(): Provider<String> =
    provider { version.toString() }


const val SNAPSHOT_VERSION_SUFFIX = "-SNAPSHOT"

fun isSnapshotVersion(version: String): Boolean =
    version.endsWith(SNAPSHOT_VERSION_SUFFIX)

// consider removing this since it's not used now
fun Project.isSnapshotVersion(): Boolean =
    isSnapshotVersion(version.toString())

const val SNAPSHOT_VERSION_REGEX = """.*$SNAPSHOT_VERSION_SUFFIX"""

fun snapshotVersionRegexOf(releaseVersionRegex: String) =
    """(?:$releaseVersionRegex)$SNAPSHOT_VERSION_SUFFIX"""

/** Matches any version that does not end with `-SNAPSHOT`. */
const val NON_SNAPSHOT_VERSION_REGEX = """(?!$SNAPSHOT_VERSION_REGEX$).*"""


/** Semver release, optionally with one of alpha/beta/rc and an extra number, e.g. `1.2.3`, `1.2.3-alpha`, `1.2.3-alpha-1`. */
const val STANDARD_RELEASE_VERSION_REGEX = """\d+\.\d+\.\d+(-(alpha|beta|rc)(-\d+)?)?"""

fun isStandardReleaseVersion(version: String): Boolean =
    version.matches(STANDARD_RELEASE_VERSION_REGEX.toRegex())
