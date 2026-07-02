package com.huanshankeji.publish.maven.central

import com.huanshankeji.isSnapshotVersion
import com.huanshankeji.versionStringProvider

plugins {
    id("com.vanniktech.maven.publish")
}

interface Extension {
    /**
     * Whether to sign publishing. This can be based on the project version. Defaults to whether the project version is not a snapshot version.
     */
    val signPublishing: Property<Boolean>
}

val extension = extensions.create<Extension>("mavenCentralPublishConventions")

extension.signPublishing.convention(versionStringProvider().map { !isSnapshotVersion(it) })

mavenPublishing {
    publishToMavenCentral()
    // TODO This way of configuration seems eager now and is likely not to work.
    if (extension.signPublishing.get())
        signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
