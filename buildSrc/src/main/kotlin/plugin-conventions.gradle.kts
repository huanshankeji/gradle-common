plugins {
    id("build-dependency-library-conventions")
    id("org.gradle.kotlin.kotlin-dsl")
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies.
    implementation(kotlin("gradle-plugin", "1.6.10"))
}

version = "0.1.3-kotlin-1.6.10"

pluginBundle {
    website = "https://github.com/huanshankeji/gradle-plugin"
    vcsUrl = "https://github.com/huanshankeji/gradle-plugin.git"
    tags = listOf("kotlin", "multiplatform")
}
