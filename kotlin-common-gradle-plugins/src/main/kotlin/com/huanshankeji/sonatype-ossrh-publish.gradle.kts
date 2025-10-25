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
        name = "CentralPortal"
        // Using Maven Central Portal Publishing API
        // Replaces deprecated OSSRH endpoints (https://central.sonatype.org/pages/ossrh-eol/)
        // For releases: automatically publishes to Maven Central
        // For snapshots: still uses OSSRH snapshot repository
        // See: https://central.sonatype.org/publish/publish-portal-api/
        val releasesRepoUrl = "https://central.sonatype.com/api/v1/publisher/upload?publishingType=AUTOMATIC"
        val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        url = uri(if (isSnapshotVersion) snapshotsRepoUrl else releasesRepoUrl)
        credentials {
            if (isSnapshotVersion) {
                // Snapshots still use OSSRH credentials
                project.findProperty("ossrhUsername")?.let { username = it as String }
                project.findProperty("ossrhPassword")?.let { password = it as String }
            } else {
                // Releases use Central Portal token authentication
                // Generate token at https://central.sonatype.com/account
                // Set both username and password to the token value
                project.findProperty("centralToken")?.let { 
                    username = it as String
                    password = it as String
                }
            }
        }
    }
}
