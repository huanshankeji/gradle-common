// TODO: this script is not tested yet.
package com.huanshankeji

plugins {
    id("com.huanshankeji.default-maven-publish")
}

// Copied and adapted from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry.
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/huanshankeji/huanshankeji")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
