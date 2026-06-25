# Huanshankeji Gradle Common (in and for Kotlin)

[![Gradle Plugin Portal (gradle-plugins)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.kotlin-multiplatform-conventional-targets?label=plugin%20portal%20%28gradle-plugins%29)](https://plugins.gradle.org/search?term=com.huanshankeji)
[![Gradle Plugin Portal (common-gradle-dependencies)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.common-gradle-dependencies-dummy-plugin?label=plugin%20portal%20%28common-gradle-dependencies%29)](https://plugins.gradle.org/plugin/com.huanshankeji.common-gradle-dependencies-dummy-plugin)

Huanshankeji's Gradle common code in Kotlin, mainly for common projects in Kotlin

## Project status and guide

This library currently mainly serves our use, and the APIs are experimental and subject to change. There are currently no detailed docs or tutorials on how to use the plugins. [Check out the API documentation here.](https://huanshankeji.github.io/gradle-common/.) See the build scripts in [kotlin-common](https://github.com/huanshankeji/kotlin-common) for examples. Browse the plugins in [the `kotlin-common-gradle-plugins` module](kotlin-common-gradle-plugins) and [the `architecture-common-gradle-plugins` module](architecture-common-gradle-plugins) as references. Instead of adding this library to your build dependencies, you can also copy the plugins to your own projects and adapt them to your own needs.

## Gradle version and Kotlin version

See [gradle/wrapper/gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties) for the Gradle wrapper version and [gradle/libs.versions.toml](gradle/libs.versions.toml) (`[versions] kotlin`) for the Kotlin version used to compile build logic and plugin sources. These versions are tested against and used by us (`./gradlew check`). There might be compatibility issues when you use other versions of Gradle or Kotlin, especially versions with different [MAJOR](https://semver.org/) versions.

This library is currently based on **Gradle 9**. There might be compatibility issues with lower versions of Gradle.

### Build-logic Kotlin vs Gradle's embedded Kotlin

Gradle's `kotlin-dsl` plugin (used in `buildSrc`) ships with an **embedded Kotlin** version (Kotlin **2.3.21** on Gradle **9.6.0**). We intentionally compile build logic and precompiled script plugins with a **newer and possibly latest Kotlin Gradle plugin** (currently **2.4.0** from the version catalog) instead of relying on that embedded version.

Gradle warns that mixing `kotlin-dsl` with a different Kotlin Gradle plugin version is unsupported. We accept that warning; `./gradlew check` passes with it. Example (safe to ignore):

```
WARNING: Unsupported Kotlin plugin version.
The `embedded-kotlin` and `kotlin-dsl` plugins rely on features of Kotlin `2.3.21` that might work differently than in the requested version `2.4.0`.
Using the `kotlin-dsl` plugin together with a different Kotlin version (for example, by using the Kotlin Gradle plugin (`kotlin(jvm)`)) in the same project is not recommended.
```

See also [Gradle: Kotlin DSL plugin](https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin).

### About the version of the Kotlin Gradle plugins

The projects and plugins depend on a certain version of the Kotlin Gradle plugins. Sometimes it's needed to specify your own version of the Kotlin Gradle plugins when using the plugins in your project. For example, a version of [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) currently supports only a certain version of the Kotlin Gradle plugins. Especially, if your desired Kotlin version is lower than this project's dependency Kotlin version, you need to exclude the transitive Kotlin dependencies.

For example, with Compose 1.3.1 in `buildSrc/build.gradle.kts`:

```kotlin
dependencies {
    implementation(kotlin("gradle-plugin", "1.8.10"))
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.3.1")

    api("com.huanshankeji:common-gradle-dependencies:0.5.0-20230310") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.4.0") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:architecture-common-gradle-plugins:0.4.0") { exclude("org.jetbrains.kotlin") }
}
```

Or:

```kotlin
dependencies {
   implementation(kotlin("gradle-plugin", "1.8.10"))
   implementation("org.jetbrains.compose:compose-gradle-plugin:1.3.1")

   api("com.huanshankeji:common-gradle-dependencies:0.5.0-20230310")
   implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.4.0")
   implementation("com.huanshankeji:architecture-common-gradle-plugins:0.4.0")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}
```

## Common Gradle dependencies

The `common-gradle-dependenicies` module includes dependencies and their versions that we use in our projects.

Please note that this project often has breaking/incompatible changes, and the Gradle plugin modules depend on a certain version of `common-gradle-dependenicies` as its library dependency. If you use both the Gradle plugins and `common-gradle-dependenicies` in your project and encounter `java.lang.NoClassDefFoundError` when loading your Gradle build, please consider updating them to matching versions.

## Developer notices

1. IntelliJ IDEA doesn't work well with applying plugins to script plugins in project sources. If a script plugin's code does not resolve, try restarting IntelliJ IDEA.
2. `buildSrc` is a multi-project build whose subprojects source-link the corresponding root modules' sources (see `buildSrc/settings.gradle.kts`), and the plugin modules depend on the `common-gradle-dependencies` project directly, so there are no longer cross-version bootstrapping dependencies on released artifacts of this repository, and there is no need to publish anything to Maven local before building.
