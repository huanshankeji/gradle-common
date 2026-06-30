package com.huanshankeji.github.packages

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
    val owner: Property<String>
    val repository: Property<String>
}

val extension = extensions.create<Extension>("githubPackagesPublish")

afterEvaluate {
    publishingRepositoriesAddGithubPackagesMavenRepository(
        owner = extension.owner.get(),
        repository = extension.repository.get()
    )
}
