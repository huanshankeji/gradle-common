package com.huanshankeji

plugins {
    // Not tested yet since migrated to this plugin instead of `maven-publish`.
    id("com.vanniktech.maven.publish")
}

interface Extension {
    val host: Property<String>
    val projectId: Property<String>
}

val extension = extensions.create<Extension>("gitlabPackagesPublish")

afterEvaluate {
    publishing {
        repositories {
            gitlabProjectLevelMavenRepository(
                this,
                host = extension.host.getOrElse(GITLAB_HOST), projectIdOrProjectPath = extension.projectId.get()
            )
        }
    }
}
