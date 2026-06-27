plugins {
    `kotlin-dsl`
    /*
    Not applied to this project; registers the Kotlin JVM plugin version (from the catalog) for
    all `buildSrc` subprojects. See `buildSrc/settings.gradle.kts` for why `pluginManagement`
    alone is not enough.
     */
    alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.gradle.kotlinDslPlugins)

    implementation(libs.gradle.pluginPublishPlugin)

    implementation(libs.dokka.gradlePlugin)

    // The source-linked team plugins, replacing the previous stale `com.huanshankeji.team:gradle-plugins` bootstrapping dependency (#54, #60).
    implementation(project(":huanshankeji-team-gradle-plugins"))
}
