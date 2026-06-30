package com.huanshankeji.maven

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
