package com.huanshankeji.gitversioning

import com.huanshankeji.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.STANDARD_RELEASE_VERSION_REGEX
import com.huanshankeji.snapshotVersionRegexOf

/**
 * Version regexes used by [conventionMavenRepositories] exclusive-content partitions
 * (snapshot / `*-dev-commit-*` / release).
 * Defaults match the standard snapshot / `*-dev-commit-*` / release convention.
 *
 * The default instance is actually equivalent to `ConventionVersionRegexes.forReleaseVersionRegex(STANDARD_RELEASE_VERSION_REGEX)`.
 */
data class ConventionVersionRegexes(
    val snapshotVersionRegex: String = SNAPSHOT_VERSION_REGEX,
    val devCommitVersionRegex: String = STANDARD_DEV_COMMIT_VERSION_REGEX,
    val releaseVersionRegex: String = STANDARD_RELEASE_VERSION_REGEX,
) {
    companion object {
        fun forReleaseVersionRegex(
            releaseVersionRegex: String,
            includeReleaseVersionRegexInSnapshotVersion: Boolean = false
        ) =
            ConventionVersionRegexes(
                if (includeReleaseVersionRegexInSnapshotVersion) snapshotVersionRegexOf(releaseVersionRegex)
                else SNAPSHOT_VERSION_REGEX,
                devCommitVersionRegexOf(releaseVersionRegex),
                releaseVersionRegex
            )
    }
}
