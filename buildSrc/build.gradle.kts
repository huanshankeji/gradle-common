plugins {
    `kotlin-dsl`
}

dependencies {
    // for `KotlinCompilationTask` and the version is compatible with the Compose version in the catalog
    // https://kotlinlang.org/docs/releases.html#release-details
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.gradleKotlinDsl.plugins)

    //https://plugins.gradle.org/plugin/com.gradle.plugin-publish
    implementation(libs.gradle.pluginPublish)

    // https://github.com/Kotlin/dokka/releases
    implementation(libs.dokka.gradlePlugin)

    // The source-linked team plugins, replacing the previous stale `com.huanshankeji.team:gradle-plugins`
    // bootstrapping dependency (#54). Brings `kotlin-common-gradle-plugins` (and its `api` dependencies)
    // transitively, so the convention plugins in this project can apply `com.huanshankeji.team.*` by id.
    implementation(project(":huanshankeji-team-gradle-plugins"))
}
