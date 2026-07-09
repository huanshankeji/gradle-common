plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    kotlin("jvm")
    // Not applied imperatively at the end because there are no plugins in this module.
    `kotlin-dsl`
}

dependencies {
    api(project(":kotlin-common:kotlin-common-gradle-library"))
}

// Source-link the `huanshankeji-team/gradle-library` sources.
sourceSets.main {
    kotlin.srcDir("../../../huanshankeji-team/gradle-library/src/main/kotlin")
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
