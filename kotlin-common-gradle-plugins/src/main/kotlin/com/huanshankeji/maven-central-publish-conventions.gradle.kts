package com.huanshankeji

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
}

afterEvaluate {
    if (!isSnapshotVersion() && !isDirtyDevCommitVersion() && !isDevCommitVersion())
        mavenPublishing.signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
