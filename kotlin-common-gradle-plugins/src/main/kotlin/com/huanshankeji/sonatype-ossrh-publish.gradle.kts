package com.huanshankeji

plugins {
    `maven-publish`
    signing
}

val isSnapshotVersion = isSnapshotVersion()

if (!isSnapshotVersion)
    signing {
        isRequired = false
        sign(publishing.publications)
    }

// wrapped in `afterEvaluate` because the project version may be set after applying this plugin, on which whether to choose the release URL or the snapshot URL depends
afterEvaluate {
    //old approach that makes `publishToMavenLocal` significantly slower when the signing key is configured in `~/.gradle/gradle.properties`
    /*
    // see: https://docs.gradle.org/current/userguide/signing_plugin.html#sec:conditional_signing
    tasks.withType<Sign>().configureEach {
        onlyIf { !isSnapshotVersion }
    }
    */

    publishing.repositories.maven {
        name = "SonatypeOssrh"
        // Using OSSRH Staging API endpoints
        // After publishing, releases require manual staging and release via https://s01.oss.sonatype.org/
        // or can be automated with the Nexus Staging Maven plugin
        // See: https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/
        val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        url = uri(if (isSnapshotVersion) snapshotsRepoUrl else releasesRepoUrl)
        credentials {
            project.findProperty("ossrhUsername")?.let { username = it as String }
            project.findProperty("ossrhPassword")?.let { password = it as String }
        }
    }
}
