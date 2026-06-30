package com.huanshankeji

import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.api.provider.Property
import org.gradle.authentication.http.HttpHeaderAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.credentials

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

publishing {
    repositories {
        maven {
            name = GITLAB_PACKAGE_REGISTRY_REPOSITORY_NAME
            val host = extension.host.orElse(GITLAB_COM_HOST)
            url = host.zip(extension.projectId) { resolvedHost, projectId ->
                uri("https://$resolvedHost/api/v4/projects/$projectId/packages/maven")
            }
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = gitlabPackageRegistryPrivateToken()
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}
