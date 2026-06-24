import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
}

dependencies {
    // Depend on the local project directly (developed and released together) instead of a
    // stale released version, removing the cross-version self-dependency (#54).
    implementation(project(":common-gradle-dependencies"))
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.InternalApi")
}
