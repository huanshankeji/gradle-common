/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


object DependencyVersions {
    val kotlin = "2.0.10" // compatible with the compose version below
    val composeMultiplatform = "1.7.0"
    val kotlinxBenchmark = "0.4.11"
}

val alignedPluginVersion = "0.6.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.8.0-20241016-SNAPSHOT"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectSourceDependentStableCommonGradleDependenciesVersion = "0.8.0-20241016-SNAPSHOT".apply {
    //require(!endsWith("SNAPSHOT")) // TODO
}
