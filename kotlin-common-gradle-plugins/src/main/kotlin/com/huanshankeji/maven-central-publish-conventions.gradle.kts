package com.huanshankeji

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    if (!isSnapshotVersion() && !isDirtyDevCommitVersion() && !isDevCommitVersion())
        signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
