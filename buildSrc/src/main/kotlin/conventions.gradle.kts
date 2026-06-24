import com.huanshankeji.publishingRepositoriesAddGithubPackagesMavenRepository

plugins {
    id("org.gradle.kotlin.kotlin-dsl")

    id("com.gradle.plugin-publish")
    id("com.vanniktech.maven.publish")
}

repositories {
    mavenLocal() // comment out when not needed
    gradlePluginPortal()
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies if the version in the "buildSrc" build script is different.
    implementation(kotlin("gradle-plugin"))
    // These 2 dependencies are implicitly added with the Gradle's embedded Kotlin version if not added explicitly.
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}

kotlin.jvmToolchain(17)

// Replaces the team `with-group` plugin.
group = "com.huanshankeji"

// Publishes this repository's own artifacts to the team's GitHub Packages registry.
// Configured directly via the shared helper from `kotlin-common-gradle-plugins` (source-linked
// into `buildSrc`) instead of applying `com.huanshankeji.team.default-github-packages-maven-publish`,
// because the team precompiled script plugins consume cross-module extension accessors that cannot
// be generated when compiled in the same `buildSrc` compilation unit (see #54).
publishingRepositoriesAddGithubPackagesMavenRepository(owner = "huanshankeji", repository = rootProject.name)


gradlePlugin {
    website.set(GITHUB_URL)
    vcsUrl.set(GITHUB_GIT_URL)
    plugins.all { tags.set(listOf("kotlin", "kotlin-multiplatform")) }
}
