plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

// Source-link the `huanshankeji-team-settings-gradle-plugins` sources (#54).
//
// IMPORTANT: the source directory must be added BEFORE the `kotlin-dsl` plugin is applied
// (https://github.com/gradle/gradle/issues/21052); see the comment in the sibling build script.
sourceSets.main {
    kotlin.srcDir("../../huanshankeji-team-settings-gradle-plugins/src/main/kotlin")
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")

kotlin {
    compilerOptions {
        optIn.addAll(
            "com.huanshankeji.GradleCommonExperimentalApi",
        )
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
