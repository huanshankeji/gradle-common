package com.huanshankeji

plugins {
    id("com.vanniktech.maven.publish")
}

logger.warn(
    "WARNING: 'com.huanshankeji.maven-central-publish-conventions' is deprecated and will be removed in a future release. " +
            "Please migrate to 'com.huanshankeji.publish.maven.central.conventions' instead."
)

mavenPublishing {
    publishToMavenCentral()

    if (!isSnapshotVersion())
        signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin