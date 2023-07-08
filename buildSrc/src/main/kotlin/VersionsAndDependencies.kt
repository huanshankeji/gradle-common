import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

val kotlinVersion = "1.8.20" // for Compose 1.4.0

val alignedPluginVersion = "0.5.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.7.0-20230621-SNAPSHOT"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectDependentStableCommonGradleDependenciesVersion = "0.7.0-20230621-SNAPSHOT".apply {
    // TODO: temporararily commented out for debugging purposes
    //require(!endsWith("SNAPSHOT"))
}
