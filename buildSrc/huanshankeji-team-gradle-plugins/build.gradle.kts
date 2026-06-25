plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    // Applied without a version because the Kotlin plugin is already on the shared `buildSrc` classpath.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    implementation(libs.gradle.kotlinDslPlugins)
    // `api`, matching the root module; provides the `com.huanshankeji.*` plugins whose extensions
    // the team plugins configure via type-safe accessors (compiled across this project boundary).
    api(project(":kotlin-common-gradle-plugins"))
}

// Source-link the `huanshankeji-team-gradle-plugins` sources (#54).
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../huanshankeji-team-gradle-plugins/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")

kotlin {
    compilerOptions {
        optIn.add("com.huanshankeji.InternalApi")
    }
}
