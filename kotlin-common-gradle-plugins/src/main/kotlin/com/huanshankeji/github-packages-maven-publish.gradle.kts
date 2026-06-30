package com.huanshankeji

import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.provider.Property
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
    val owner: Property<String>
    val repository: Property<String>
}

val extension = extensions.create<Extension>("githubPackagesPublish")

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = extension.owner.zip(extension.repository) { owner, repository ->
                uri("https://maven.pkg.github.com/$owner/$repository")
            }
            credentials(PasswordCredentials::class) {
                username = githubPackagesMavenUsername()
                password = githubPackagesMavenPassword()
            }
        }
    }
}
