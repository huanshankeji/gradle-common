package com.huanshankeji.gitlab.packageregistry.maven

import com.huanshankeji.publish.publishing

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

val extension = extensions.create<Extension>("gitlabPackageRegistryProjectLevelEndpointMavenPublish")

publishing {
    repositories {
        context(providers, ::uri) {
            gitlabPackageRegistryProjectLevelEndpointMavenRepository(
                hostProvider = extension.host.orElse(GITLAB_COM_HOST),
                projectIdOrProjectPathProvider = extension.projectId,
            )
        }
    }
}
