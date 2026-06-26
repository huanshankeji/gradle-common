plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    // Applied without a version; version comes from `alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false` in the `buildSrc` root build script.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.bundles.kotlinCommonGradlePlugins.implementation)
    api(libs.bundles.kotlinCommonGradlePlugins.api)

    implementation(project(":common-gradle-dependencies"))
}

// Source-link the `kotlin-common-gradle-plugins` sources (#54).
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../kotlin-common-gradle-plugins/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")

kotlin {
    compilerOptions {
        optIn.add("com.huanshankeji.InternalApi")
    }
}
