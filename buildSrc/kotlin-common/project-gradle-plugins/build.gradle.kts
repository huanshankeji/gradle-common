plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.bundles.kotlinCommonGradlePlugins.implementation)
    // Keep in sync with `CommonVersions.kotlinxBenchmark` (this script cannot see the root
    // buildSrc classpath helper used by the corresponding root module build script).
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-plugin:0.4.16")
    api(libs.bundles.kotlinCommonGradlePlugins.api)

    implementation(project(":common-gradle-dependencies"))
    implementation(project(":kotlin-common:kotlin-common-gradle-library"))
}

// Source-link the `kotlin-common/project-gradle-plugins` sources.
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../../kotlin-common/project-gradle-plugins/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")


// Copied from `aligned-version-build-logic-conventions.gradle.kts`. Keep consistent with it.

kotlin {
    compilerOptions {
        optIn.addAll(
            "com.huanshankeji.GradleCommonInternalApi",
            "com.huanshankeji.GradleCommonExperimentalApi",
        )
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
