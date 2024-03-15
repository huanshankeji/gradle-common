import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

val kotlinVersion = "1.9.23" // for Compose 1.6.1

val alignedPluginVersion = "0.5.1-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.7.1-20240314"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectSourceDependentStableCommonGradleDependenciesVersion = "0.7.1-20240314-boostrap".apply {
    require(!endsWith("SNAPSHOT"))
}
