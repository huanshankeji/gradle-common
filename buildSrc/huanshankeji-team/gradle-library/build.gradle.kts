plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

group = "team"

dependencies {
    implementation(kotlin("gradle-plugin"))
    compileOnly(gradleApi())
    api(project(":kotlin-common:kotlin-common-gradle-library"))
}

// Source-link the `huanshankeji-team/gradle-library` sources (#54).
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../../huanshankeji-team/gradle-library/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")
