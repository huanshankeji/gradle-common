package com.huanshankeji

plugins {
    id("com.huanshankeji.default-maven-publish")
    signing
}

signing {
    isRequired = false
    sign(publishing.publications)
}

publishing.repositories.maven {
    val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
    credentials {
        project.findProperty("ossrhUsername")?.let { username = it as String }
        project.findProperty("ossrhPassword")?.let { password = it as String }
    }
}
