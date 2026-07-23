package com.huanshankeji.github.packages.maven

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

val extension = extensions.create<GithubPackagesMavenPublishExtension>("githubPackagesMavenPublish")

publishing {
    repositories {
        context(providers, ::uri) {
            githubPackagesMavenRegistry(
                ownerProvider = extension.owner,
                repositoryProvider = extension.repository,
            )
        }
    }
}
