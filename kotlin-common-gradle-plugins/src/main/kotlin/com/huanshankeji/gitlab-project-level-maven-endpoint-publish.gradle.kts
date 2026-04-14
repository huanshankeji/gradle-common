package com.huanshankeji

plugins {
    `maven-publish`
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
                this@repositories,
                host = extension.host.getOrElse(GITLAB_HOST), projectIdOrProjectPath = extension.projectId.get()
            )
        }
    }
}
