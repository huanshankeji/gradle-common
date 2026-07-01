package com.huanshankeji

import com.huanshankeji.publish.publishing

// This plugin is deprecated and can be removed directly in the future.
// TODO deprecation warnings

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
    publishing {
        repositories {
            @Suppress("DEPRECATION")
            gitlabProjectLevelMavenRepository(
                this,
                host = extension.host.getOrElse(GITLAB_HOST),
                projectIdOrProjectPath = extension.projectId.get(),
            )
        }
    }
}
