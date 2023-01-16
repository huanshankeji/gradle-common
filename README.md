# Huanshankeji Gradle Common (in and for Kotlin)

[![Gradle Plugin Portal (gradle-plugins)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.kotlin-jvm-common-conventions?label=plugin%20portal%20%28gradle-plugins%29)](https://plugins.gradle.org/search?term=com.huanshankeji)
[![Gradle Plugin Portal (common-gradle-dependencies)](https://img.shields.io/gradle-plugin-portal/v/com.huanshankeji.dummy-plugin?label=plugin%20portal%20%28common-gradle-dependencies%29)](https://plugins.gradle.org/plugin/com.huanshankeji.dummy-plugin)

Huanshankeji's Gradle common code in Kotlin, mainly for common projects in Kotlin

## About the version of the Kotlin Gradle plugins

The projects and plugins depend on a certain version of the Kotlin Gradle plugins. Sometimes it's needed to specify your
own version of the Kotlin Gradle plugins when using the plugins in the project. For example, a version
of [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) currently supports only a certain version of the
Kotlin Gradle plugins. Especially, if your desired Kotlin version is lower than this project's dependent Kotlin version,
you need to exclude the transitive Kotlin dependencies.

For example, with Compose 1.2.2 in `buildSrc/build.gradle.kts`:

```kotlin
dependencies {
    implementation(kotlin("gradle-plugin", "1.7.20"))
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.2.2")

    api("com.huanshankeji:common-gradle-dependencies:0.3.2-20220728") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.3.2") { exclude("org.jetbrains.kotlin") }
    implementation("com.huanshankeji:architecture-common-gradle-plugins:0.3.2") { exclude("org.jetbrains.kotlin") }
}
```

Or:

```kotlin
dependencies {
   implementation(kotlin("gradle-plugin", "1.7.20"))
   implementation("org.jetbrains.compose:compose-gradle-plugin:1.2.2")

   api("com.huanshankeji:common-gradle-dependencies:0.3.2-20220728")
   implementation("com.huanshankeji:kotlin-common-gradle-plugins:0.3.2")
   implementation("com.huanshankeji:architecture-common-gradle-plugins:0.3.2")
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}
```

## Notices

1. IntelliJ IDEA doesn't work well with applying plugins to script plugins in project sources. If a script plugin's code
   does not resolve, try restarting IntelliJ IDEA.
