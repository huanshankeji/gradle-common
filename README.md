# Huanshankeji Gradle Common (in and for Kotlin)

[![Gradle Plugin Portal (gradle-plugins)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.kotlin-jvm-common-conventions?label=plugin%20portal%20%28gradle-plugins%29)](https://plugins.gradle.org/search?term=com.huanshankeji)
[![Gradle Plugin Portal (common-gradle-dependencies)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.common-gradle-dependencies-dummy-plugin?label=plugin%20portal%20%28common-gradle-dependencies%29)](https://plugins.gradle.org/plugin/com.huanshankeji.common-gradle-dependencies-dummy-plugin)

Huanshankeji's Gradle common code in Kotlin, mainly for common projects in Kotlin

## Examples

There are currently no docs or tutorials on how to use the plugins. See the build scripts in [kotlin-common](https://github.com/huanshankeji/kotlin-common) for examples.

## Gradle version and Kotlin version

See [gradle/wrapper/gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties) for the currently dependent Gradle version and [buildSrc/build.gradle.kts](buildSrc/build.gradle.kts) for the currently dependent Kotlin version. These versions are tested against and used by us. There might be compatibility issues when you use other versions of Gradle or Kotlin, especially versions with different [MAJOR](https://semver.org/) versions.

### About the version of the Kotlin Gradle plugins

The projects and plugins depend on a certain version of the Kotlin Gradle plugins. Sometimes it's needed to specify your own version of the Kotlin Gradle plugins when using the plugins in your project. For example, a version of [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) currently supports only a certain version of the Kotlin Gradle plugins. Especially, if your desired Kotlin version is lower than this project's dependent Kotlin version, you need to exclude the transitive Kotlin dependencies.

For example, with Compose 1.3.1 in `buildSrc/build.gradle.kts`:

```kotlin
dependencies {
    implementation(kotlin("gradle-plugin", "1.8.10"))
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.3.1")

    api("com.huanshankeji:common-gradle-dependencies:0.5.0-20230310") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.3.2") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:architecture-common-gradle-plugins:0.3.2") { exclude("org.jetbrains.kotlin") }
}
```

Or:

```kotlin
dependencies {
   implementation(kotlin("gradle-plugin", "1.8.10"))
   implementation("org.jetbrains.compose:compose-gradle-plugin:1.3.1")

   api("com.huanshankeji:common-gradle-dependencies:0.5.0-20230310")
   implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.3.2")
   implementation("com.huanshankeji:architecture-common-gradle-plugins:0.3.2")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}
```

## Developer notices

1. IntelliJ IDEA doesn't work well with applying plugins to script plugins in project sources. If a script plugin's code does not resolve, try restarting IntelliJ IDEA.
