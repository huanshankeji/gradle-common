package com.huanshankeji

// This plugin is deprecated and can be removed directly in the future.

plugins {
    /*
    This plugin has special fast-path / custom behavior only for Maven Central,
    while “other repositories” are delegated to plain Gradle maven-publish upload tasks.
    Because of the slowness especially for multi-module Kotlin Multiplatform projects,
    when running the task for multiple projects, run in parallel.
     */
    id("com.vanniktech.maven.publish")
}

logger.warn(
    "WARNING: 'com.huanshankeji.github-packages-maven-publish' is deprecated and will be removed in a future release. " +
            "Please migrate to 'com.huanshankeji.github.packages.maven.publish' instead."
)

interface Extension {
    val owner: Property<String>
    val repository: Property<String>
}

val extension = extensions.create<Extension>("githubPackagesPublish")

afterEvaluate {
    @Suppress("DEPRECATION")
    publishingRepositoriesAddGithubPackagesMavenRepository(
        owner = extension.owner.get(),
        repository = extension.repository.get()
    )
}
