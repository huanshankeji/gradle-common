plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    // Applied without a version because the Kotlin plugin is already on the shared `buildSrc` classpath.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    implementation(libs.gradle.kotlinDslPlugins)
}

// Source-link the `common-gradle-dependencies` sources so the build logic is compiled from
// the current source instead of a stale released version (#54).
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied,
// because it reads the precompiled-script-plugin source directories eagerly at apply time
// (https://github.com/gradle/gradle/issues/21052).
sourceSets.main {
    kotlin.srcDir("../../common-gradle-dependencies/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")
