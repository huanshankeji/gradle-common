package com.huanshankeji

plugins {
    /*
    This plugin has special fast-path / custom behavior only for Maven Central,
    while “other repositories” are delegated to plain Gradle maven-publish upload tasks.
    Because of the slowness especially for multi-module Kotlin Multiplatform projects,
    when running the task for multiple projects, run in parallel.
     */
    id("com.vanniktech.maven.publish")
}

interface Extension {
    val host: Property<String>
    val projectId: Property<String>
}

val extension = extensions.create<Extension>("gitlabPackageRegistryProjectLevelMavenEndpointPublish")

afterEvaluate {
    with(this) {
        publishing {
            repositories {
                gitlabPackageRegistryProjectLevelMavenRepository(
                    host = extension.host.getOrElse(GITLAB_COM_HOST),
                    projectIdOrProjectPath = extension.projectId.get(),
                )
            }
        }
    }
}
