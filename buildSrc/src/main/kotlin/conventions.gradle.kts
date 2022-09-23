plugins {
    id("org.gradle.kotlin.kotlin-dsl")
    id("com.huanshankeji.team.with-group")

    id("com.huanshankeji.java-1-8-compatibility-publish-conventions")
    id("com.gradle.plugin-publish")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies.
    implementation(kotlin("gradle-plugin", kotlinVersion))
}


pluginBundle {
    website = GITHUB_URL
    vcsUrl = GITHUB_GIT_URL
    tags = listOf("kotlin", "multiplatform")
}
