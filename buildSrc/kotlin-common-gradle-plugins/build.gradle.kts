import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    // Provides `sourceSets`/`kotlin {}` so the source directory can be configured before `kotlin-dsl`.
    // Applied without a version because the Kotlin plugin is already on the shared `buildSrc` classpath.
    kotlin("jvm")
    // Applied imperatively at the end (see below).
    `kotlin-dsl` apply false
}

dependencies {
    implementation(libs.gradleKotlinDsl.plugins)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.bundles.kotlinCommonGradlePluginsImplementation)
    // made `api` to expose the plugin extension class, matching the root module
    api(libs.vanniktech.mavenPublish.gradlePlugin)

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

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.InternalApi")
}
