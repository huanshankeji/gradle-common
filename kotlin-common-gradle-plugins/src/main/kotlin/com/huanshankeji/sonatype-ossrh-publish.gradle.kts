package com.huanshankeji

import org.gradle.internal.deprecation.DeprecationLogger

// deprecated

plugins {
    `maven-publish`
    signing
}

DeprecationLogger.deprecatePlugin(
    "The `com.huanshankeji.*sonatype-ossrh-publish*` plugins are deprecated. " +
            "Please migrate to `com.vanniktech.maven.publish` or `com.huanshankeji.maven-central-publish-conventions`. " +
            "Also see <https://central.sonatype.org/pages/ossrh-eol/>."
)

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
        /*
        // These Publisher API URLs wouldn't work here
        // https://central.sonatype.org/publish/publish-portal-api/
        val releasesRepoUrl = "https://central.sonatype.com/api/v1/publisher/upload"
        val snapshotsRepoUrl = "https://central.sonatype.com/repository/maven-snapshots/"
        */
        // https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/#getting-started-for-maven-api-like-plugins
        val releasesRepoUrl = "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"
        val snapshotsRepoUrl = "https://central.sonatype.com/repository/maven-snapshots/" // not working as tested
        url = uri(if (isSnapshotVersion) snapshotsRepoUrl else releasesRepoUrl)
        credentials {
            project.findProperty("ossrhUsername")?.let { username = it as String }
            project.findProperty("ossrhPassword")?.let { password = it as String }
        }
    }
}
