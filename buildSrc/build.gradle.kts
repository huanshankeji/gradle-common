import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    `embedded-kotlin`
    // Applied imperatively at the end of this script (see below).
    `kotlin-dsl` apply false
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // for `KotlinCompilationTask` and the version is compatible with the Compose version in the catalog
    // https://kotlinlang.org/docs/releases.html#release-details
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.gradleKotlinDsl.plugins)

    //https://plugins.gradle.org/plugin/com.gradle.plugin-publish
    implementation(libs.gradle.pluginPublish)

    // The dependencies needed to compile the source-linked plugin modules below.
    implementation(libs.vanniktech.mavenPublish.gradlePlugin)
    implementation(libs.bundles.kotlinCommonGradlePluginsImplementation)
}

// Source-link the shareable plugin module sources so `buildSrc` compiles them from
// the current source instead of depending on a stale released version of these plugins
// (the previous cross-version self-dependency, see #54).
//
// Only `common-gradle-dependencies` and `kotlin-common-gradle-plugins` are linked:
// - `architecture-common-gradle-plugins` only contributes product plugins (Compose/web)
//   that are never applied to build this repository.
// - `huanshankeji-team-gradle-plugins` is intentionally NOT linked: its precompiled
//   script plugins consume type-safe accessors for extensions defined by
//   `kotlin-common-gradle-plugins` plugins (e.g. `githubPackagesPublish`, `dokkaConvention`).
//   Those accessors are only generated across a project/binary boundary, so the team
//   plugins cannot be compiled in the same `buildSrc` compilation unit. Instead,
//   `conventions.gradle.kts` configures this repository's own GitHub Packages publishing
//   directly via the shared helper functions from `kotlin-common-gradle-plugins`.
//
// IMPORTANT: these source directories must be added BEFORE the `kotlin-dsl` plugin is
// applied, because it reads the precompiled-script-plugin source directories eagerly at
// apply time (https://github.com/gradle/gradle/issues/21052). Hence `kotlin-dsl` is
// declared with `apply false` above and applied imperatively after this block.
sourceSets.main {
    kotlin.srcDirs(
        "../common-gradle-dependencies/src/main/kotlin",
        "../kotlin-common-gradle-plugins/src/main/kotlin",
    )
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    // The source-linked sources use `@InternalApi`, matching `aligned-version-plugin-conventions`.
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.InternalApi")
}
