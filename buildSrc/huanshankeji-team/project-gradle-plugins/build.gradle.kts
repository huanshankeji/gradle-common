plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    // Copied from `project-gradle-plugins-conventions.gradle.kts`. Keep consistent with it.
    implementation(kotlin("gradle-plugin"))
    implementation(project(":common-gradle-dependencies"))
    // `api`, matching the root module; provides the `com.huanshankeji.*` plugins whose extensions
    // the team plugins configure via type-safe accessors (compiled across this project boundary).
    api(project(":kotlin-common:kotlin-common-project-gradle-plugins"))
    api(project(":huanshankeji-team:gradle-library"))
}

// Source-link the `huanshankeji-team/project-gradle-plugins` sources.
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../../huanshankeji-team/project-gradle-plugins/src/main/kotlin")
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
