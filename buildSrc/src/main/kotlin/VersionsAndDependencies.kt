/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


object DependencyVersions {
    // https://kotlinlang.org/docs/releases.html#release-details
    val kotlin = "2.2.21" // compatible with the compose version below

    // https://github.com/JetBrains/compose-multiplatform/releases
    val composeMultiplatform = "1.9.1"

    // https://github.com/Kotlin/kotlinx-benchmark/releases
    val kotlinxBenchmark = "0.4.14"

    // https://github.com/Kotlin/dokka/releases
    val dokka = "2.1.0"

    // https://github.com/Kotlin/binary-compatibility-validator/releases
    val binaryCompatibilityValidator = "0.18.1"

    // https://github.com/vanniktech/gradle-maven-publish-plugin/releases
    val vanniktechMavenPublish = "0.34.0"
}

val alignedPluginVersion = "0.11.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.10.0-20251110-SNAPSHOT"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectSourceDependencyStableCommonGradleDependenciesVersion = "0.10.0-20251024".apply {
    require(!endsWith("SNAPSHOT")) // comment this out when debugging and testing snapshots
}
