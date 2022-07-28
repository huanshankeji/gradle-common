plugins {
    id("build-dependency-library-conventions")
    id("org.gradle.kotlin.kotlin-dsl")
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies.
    implementation(kotlin("gradle-plugin", kotlinVersion))
}

version = "0.1.9-SNAPSHOT"

pluginBundle {
    website = "https://github.com/huanshankeji/gradle-common"
    vcsUrl = "$website.git"
    tags = listOf("kotlin", "multiplatform")
}
