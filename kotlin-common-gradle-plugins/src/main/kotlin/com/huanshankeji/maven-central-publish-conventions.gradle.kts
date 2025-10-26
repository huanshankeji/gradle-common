package com.huanshankeji

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral()

    if (!isSnapshotVersion())
        signAllPublications()
}
