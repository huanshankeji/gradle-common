package com.huanshankeji.gitversioning

import com.huanshankeji.GradleCommonExperimentalApi
import com.huanshankeji.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.STANDARD_RELEASE_VERSION_REGEX
import org.gradle.api.Project

// consider removing this since it's not used now
/**
 * This check is simpler compared to [DEV_COMMIT_VERSION_REGEX].
 */
@GradleCommonExperimentalApi
fun isDevCommitVersion(version: String): Boolean =
    version.contains("-dev-commit-")

// consider removing this since it's not used now
@GradleCommonExperimentalApi
fun Project.isDevCommitVersion(): Boolean =
    isDevCommitVersion(version.toString())

// Alternative matching exactly the number of Git hash digits: `"""-dev-commit-[0-9a-f]{40}"""`
const val DEV_COMMIT_VERSION_REGEX_SUFFIX = """-dev-commit-[0-9a-f]+"""

const val DEV_COMMIT_VERSION_REGEX = """.*$DEV_COMMIT_VERSION_REGEX_SUFFIX"""

fun devCommitVersionRegexOf(releaseVersionRegex: String) =
    """(?:$releaseVersionRegex)$DEV_COMMIT_VERSION_REGEX_SUFFIX"""

/** Clean committed dev build, e.g. `1.0.0-dev-commit-0123456789abcdef0123456789abcdef01234567`. */
val STANDARD_DEV_COMMIT_VERSION_REGEX = devCommitVersionRegexOf(STANDARD_RELEASE_VERSION_REGEX)


// consider removing this since it's not used now
@GradleCommonExperimentalApi
fun isDirtyDevCommitVersion(version: String): Boolean =
    version.endsWith("-dirty-SNAPSHOT")

// consider removing this since it's not used now
@GradleCommonExperimentalApi
fun Project.isDirtyDevCommitVersion(): Boolean =
    isDirtyDevCommitVersion(version.toString())

/** Matches [SNAPSHOT_VERSION_REGEX] or [STANDARD_DEV_COMMIT_VERSION_REGEX]. */
val SNAPSHOT_AND_STANDARD_DEV_COMMIT_VERSION_REGEX = "$SNAPSHOT_VERSION_REGEX|$STANDARD_DEV_COMMIT_VERSION_REGEX"


/** Matches [STANDARD_DEV_COMMIT_VERSION_REGEX] or [STANDARD_RELEASE_VERSION_REGEX]. Not used now. */
val STANDARD_DEV_COMMIT_AND_RELEASE_VERSION_REGEX =
    "$STANDARD_DEV_COMMIT_VERSION_REGEX|$STANDARD_RELEASE_VERSION_REGEX"
