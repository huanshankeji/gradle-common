plugins {
    id("build-dependency-library-conventions")
    id("org.gradle.kotlin.kotlin-dsl")
    id("com.gradle.plugin-publish")
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies.
    implementation(kotlin("gradle-plugin", kotlinVersion))
}

version = "0.3.0-SNAPSHOT"

pluginBundle {
    website = GITHUB_URL
    vcsUrl = GITHUB_GIT_URL
    tags = listOf("kotlin", "multiplatform")
}
