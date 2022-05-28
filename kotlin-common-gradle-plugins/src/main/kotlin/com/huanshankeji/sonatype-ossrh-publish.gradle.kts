package com.huanshankeji

plugins {
    id("com.huanshankeji.default-maven-publish")
    signing
}

signing {
    sign(publishing.publications)
}

publishing.repositories.maven {
    val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
    credentials {
        username = project.property("ossrhUsername") as String
        password = project.property("ossrhPassword") as String
    }
}
