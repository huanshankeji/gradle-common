plugins {
    `kotlin-dsl`
    // replaced by the new approach in `settings.gradle.kts`
    //alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.gradle.kotlinDslPlugins)

    implementation(libs.gradle.pluginPublishPlugin)

    implementation(libs.dokka.gradlePlugin)

    // Expose `CommonVersions` / `CommonGradleClasspathDependencies` to root module build scripts
    // (and to this project's `VersionsAndDependencies.kt`) without duplicating those versions in
    // the version catalog.
    implementation(project(":common-gradle-dependencies"))

    // The source-linked team plugins, replacing the previous stale `com.huanshankeji.team:gradle-plugins` bootstrapping dependency.
    implementation(project(":huanshankeji-team:project-gradle-plugins"))
}
