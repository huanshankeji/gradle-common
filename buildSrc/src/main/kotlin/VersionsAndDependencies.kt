/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


object DependencyVersions {
    val kotlin = "2.0.10" // compatible with the compose version below
    val composeMultiplatform = "1.6.11"
    val kotlinxBenchmark = "0.4.10"
}

val alignedPluginVersion = "0.6.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.7.2-20240614-SNAPSHOT"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectSourceDependentStableCommonGradleDependenciesVersion = "0.7.1-20240314-boostrap".apply {
    require(!endsWith("SNAPSHOT"))
}
