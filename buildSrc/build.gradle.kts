plugins {
    `kotlin-dsl`
    // replaced by the new approach in `settings.gradle.kts`
    //alias(libs.plugins.kotlin.jvmWithExplicitVersion) apply false
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.gradle.kotlinDslPlugins)

    implementation(libs.gradle.pluginPublishPlugin)

    implementation(libs.dokka.gradlePlugin)

    // The source-linked team plugins, replacing the previous stale `com.huanshankeji.team:gradle-plugins` bootstrapping dependency (#54, #60).
    implementation(project(":huanshankeji-team-gradle-plugins"))
}
