package com.huanshankeji.team.gitversioning

import com.huanshankeji.gitversioning.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.RELEASE_VERSION_REGEX
import com.huanshankeji.gitversioning.SNAPSHOT_VERSION_REGEX
import com.huanshankeji.team.HUANSHANKEJI_GROUP
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor
import org.gradle.api.artifacts.repositories.RepositoryContentDescriptor

const val HUANSHANKEJI_MAVEN_GROUP = HUANSHANKEJI_GROUP

/** Regex-escaped [HUANSHANKEJI_MAVEN_GROUP] for `include*ByRegex` / `exclude*ByRegex`. */
const val HUANSHANKEJI_MAVEN_GROUP_REGEX = "com\\.huanshankeji"

fun MavenArtifactRepository.contentIncludeGroupVersions(
    groupRegex: String,
    versionRegex: String,
) {
    content {
        includeVersionByRegex(groupRegex, ".*", versionRegex)
    }
}

fun MavenArtifactRepository.contentExcludeGroupVersions(
    groupRegex: String,
    versionRegex: String,
) {
    content {
        excludeVersionByRegex(groupRegex, ".*", versionRegex)
    }
}

fun MavenArtifactRepository.contentIncludeModuleVersions(
    groupRegex: String,
    moduleRegex: String,
    versionRegex: String,
) {
    content {
        includeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
}

fun MavenArtifactRepository.contentExcludeModuleVersions(
    groupRegex: String,
    moduleRegex: String,
    versionRegex: String,
) {
    content {
        excludeVersionByRegex(groupRegex, moduleRegex, versionRegex)
    }
}

/** Full-version match for [RELEASE_VERSION_REGEX]. */
const val ANCHORED_RELEASE_VERSION_REGEX = "^$RELEASE_VERSION_REGEX$"

fun RepositoryContentDescriptor.includeSnapshotAndDevCommitVersions(
    groupRegex: String = ".*",
    moduleRegex: String = ".*",
) {
    includeVersionByRegex(groupRegex, moduleRegex, SNAPSHOT_VERSION_REGEX)
    includeVersionByRegex(groupRegex, moduleRegex, DEV_COMMIT_VERSION_REGEX)
}

fun RepositoryContentDescriptor.includeDevCommitVersions(
    groupRegex: String = ".*",
    moduleRegex: String = ".*",
) {
    includeVersionByRegex(groupRegex, moduleRegex, DEV_COMMIT_VERSION_REGEX)
}

fun RepositoryContentDescriptor.includeReleaseVersions(
    groupRegex: String = ".*",
    moduleRegex: String = ".*",
) {
    includeVersionByRegex(groupRegex, moduleRegex, ANCHORED_RELEASE_VERSION_REGEX)
}

fun RepositoryContentDescriptor.includeDevCommitAndReleaseVersions(
    groupRegex: String = ".*",
    moduleRegex: String = ".*",
) {
    includeDevCommitVersions(groupRegex, moduleRegex)
    includeReleaseVersions(groupRegex, moduleRegex)
}

/** For `google { mavenContent { } }` — KMP template groups. */
fun MavenRepositoryContentDescriptor.includeGoogleMavenGroups() {
    includeGroupAndSubgroups("androidx")
    includeGroupAndSubgroups("com.android")
    includeGroupAndSubgroups("com.google")
}
