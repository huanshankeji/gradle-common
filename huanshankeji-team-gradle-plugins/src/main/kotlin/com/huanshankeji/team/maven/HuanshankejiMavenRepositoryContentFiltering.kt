package com.huanshankeji.team.maven

import com.huanshankeji.gitversion.DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversion.DIRTY_DEV_COMMIT_VERSION_REGEX
import com.huanshankeji.gitversion.LEGACY_SNAPSHOT_VERSION_REGEX
import com.huanshankeji.maven.contentExcludeGroupVersions
import com.huanshankeji.maven.contentIncludeGroupVersions
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

const val HUANSHANKEJI_MAVEN_GROUP = "com.huanshankeji"

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
