plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Not applied imperatively at the end because there are no plugins in this module.
    `kotlin-dsl`
}

// Source-link the `kotlin-common/gradle-library` sources.
sourceSets.main {
    kotlin.srcDir("../../../kotlin-common/gradle-library/src/main/kotlin")
}


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
