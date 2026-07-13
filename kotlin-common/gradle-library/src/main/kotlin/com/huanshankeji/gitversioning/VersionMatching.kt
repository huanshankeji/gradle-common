package com.huanshankeji.gitversioning

import com.huanshankeji.RELEASE_VERSION_REGEX
import com.huanshankeji.SNAPSHOT_VERSION_REGEX
import org.gradle.api.Project

fun isDevCommitVersion(version: String): Boolean =
    version.contains("-dev-commit-")

// consider removing this since it's not used now
fun Project.isDevCommitVersion(): Boolean =
    isDevCommitVersion(version.toString())

/** Clean committed dev build, e.g. `1.0.0-dev-commit-abc123`. */
const val DEV_COMMIT_VERSION_REGEX = """$RELEASE_VERSION_REGEX-dev-commit-[0-9a-f]+"""


fun isDirtyDevCommitVersion(version: String): Boolean =
    version.endsWith("-dirty-SNAPSHOT")

// consider removing this since it's not used now
fun Project.isDirtyDevCommitVersion(): Boolean =
    isDirtyDevCommitVersion(version.toString())

/** Matches [SNAPSHOT_VERSION_REGEX] or [DEV_COMMIT_VERSION_REGEX]. */
const val SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX = "$SNAPSHOT_VERSION_REGEX|$DEV_COMMIT_VERSION_REGEX"


/** Matches [DEV_COMMIT_VERSION_REGEX] or [RELEASE_VERSION_REGEX]. Not used now. */
const val DEV_COMMIT_AND_RELEASE_VERSION_REGEX = "$DEV_COMMIT_VERSION_REGEX|$RELEASE_VERSION_REGEX"
