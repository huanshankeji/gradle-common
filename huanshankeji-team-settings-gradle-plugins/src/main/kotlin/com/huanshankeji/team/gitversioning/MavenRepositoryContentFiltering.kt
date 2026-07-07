package com.huanshankeji.team.gitversioning

import com.huanshankeji.gitversioning.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.DIRTY_DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversioning.LEGACY_SNAPSHOT_VERSION_REGEX
import com.huanshankeji.team.HUANSHANKEJI_GROUP
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

const val HUANSHANKEJI_MAVEN_GROUP = HUANSHANKEJI_GROUP

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

fun MavenArtifactRepository.contentIncludeHuanshankejiDirtyAndLegacySnapshots() {
    contentIncludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, DIRTY_DEV_COMMIT_VERSION_REGEX)
    contentIncludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, LEGACY_SNAPSHOT_VERSION_REGEX)
}

fun MavenArtifactRepository.contentIncludeHuanshankejiDevCommitVersions() {
    contentIncludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, DEV_COMMIT_VERSION_REGEX)
}

fun MavenArtifactRepository.contentExcludeHuanshankejiNonStableVersions() {
    contentExcludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, DEV_COMMIT_VERSION_REGEX)
    contentExcludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, DIRTY_DEV_COMMIT_VERSION_REGEX)
    contentExcludeGroupVersions(HUANSHANKEJI_MAVEN_GROUP, LEGACY_SNAPSHOT_VERSION_REGEX)
}
