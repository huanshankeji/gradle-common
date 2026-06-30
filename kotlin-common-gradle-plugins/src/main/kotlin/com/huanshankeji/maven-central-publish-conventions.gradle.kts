package com.huanshankeji

import com.huanshankeji.gitversion.isDevCommitVersion
import com.huanshankeji.gitversion.isDirtyDevCommitVersion
import com.huanshankeji.gitversion.isSnapshotVersion

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral()
}

/*
Currently added to wait for the version to resolve.
TODO Avoid `afterEvaluate` and put this back into `mavenPublishing` above by refactoring related plugins to use `Provider` APIs.
 */
afterEvaluate {
    if (!isSnapshotVersion() && !isDirtyDevCommitVersion() && !isDevCommitVersion())
        mavenPublishing.signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
