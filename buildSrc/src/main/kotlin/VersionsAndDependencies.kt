/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


object DependencyVersions {
    // https://kotlinlang.org/docs/releases.html#release-details
    val kotlin = "2.4.0" // compatible with the compose version below

    // https://github.com/JetBrains/compose-multiplatform/releases
    val composeMultiplatform = "1.10.3"

    // https://github.com/Kotlin/kotlinx-benchmark/releases
    val kotlinxBenchmark = "0.4.16"

    // https://github.com/Kotlin/dokka/releases
    val dokka = "2.2.0"

    // https://github.com/gradle-nexus/publish-plugin/releases - replaced by Kotlin abiValidation in consumers
    val binaryCompatibilityValidator = "0.18.1"

    // https://github.com/vanniktech/gradle-maven-publish-plugin/releases
    val vanniktechMavenPublish = "0.36.0"

    // https://github.com/ben-manes/gradle-versions-plugin/releases
    val benManesGradleVersionsPlugin = "0.54.0"
}

val alignedPluginBaseVersion = "0.99.0-portal-ci-test"

// "x.y.z-yyyyMMdd" — semantic org version and date when dependency versions were updated.
// Dev-commit suffixes are applied in :common-gradle-dependencies (see its build.gradle.kts).
val commonGradleDependenciesBaseVersion = "0.99.0-portal-ci-test"
