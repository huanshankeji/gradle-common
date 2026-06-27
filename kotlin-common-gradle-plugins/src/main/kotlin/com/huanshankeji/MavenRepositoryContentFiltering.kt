package com.huanshankeji

import org.gradle.api.artifacts.repositories.MavenArtifactRepository

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
