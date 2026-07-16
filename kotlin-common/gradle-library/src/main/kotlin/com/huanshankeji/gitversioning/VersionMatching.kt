package com.huanshankeji.gitversioning

import com.huanshankeji.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.STANDARD_RELEASE_VERSION_REGEX
import org.gradle.api.Project

fun isDevCommitVersion(version: String): Boolean =
    version.contains("-dev-commit-")

// consider removing this since it's not used now
fun Project.isDevCommitVersion(): Boolean =
    isDevCommitVersion(version.toString())

const val DEV_COMMIT_VERSION_REGEX = """.*-dev-commit-[0-9a-f]+"""

/** Clean committed dev build, e.g. `1.0.0-dev-commit-abc123`. */
const val STANDARD_DEV_COMMIT_VERSION_REGEX = """$STANDARD_RELEASE_VERSION_REGEX-dev-commit-[0-9a-f]+"""


fun isDirtyDevCommitVersion(version: String): Boolean =
    version.endsWith("-dirty-SNAPSHOT")

// consider removing this since it's not used now
fun Project.isDirtyDevCommitVersion(): Boolean =
    isDirtyDevCommitVersion(version.toString())

/** Matches [SNAPSHOT_VERSION_REGEX] or [STANDARD_DEV_COMMIT_VERSION_REGEX]. */
const val SNAPSHOT_AND_STANDARD_DEV_COMMIT_VERSION_REGEX = "$SNAPSHOT_VERSION_REGEX|$STANDARD_DEV_COMMIT_VERSION_REGEX"


/** Matches [STANDARD_DEV_COMMIT_VERSION_REGEX] or [STANDARD_RELEASE_VERSION_REGEX]. Not used now. */
const val STANDARD_DEV_COMMIT_AND_RELEASE_VERSION_REGEX =
    "$STANDARD_DEV_COMMIT_VERSION_REGEX|$STANDARD_RELEASE_VERSION_REGEX"
