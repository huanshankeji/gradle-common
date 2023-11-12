import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

val kotlinVersion = "1.9.20" // for Compose 1.4.0 // TODO remove this comment

val alignedPluginVersion = "0.5.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.7.0-20231111-SNAPSHOT"

// This is the source dependency version. There is another build source dependency in "buildSrc/build.gradle.kts".
val pluginProjectDependentStableCommonGradleDependenciesVersion = "0.7.0-20231111".apply {
    require(!endsWith("SNAPSHOT"))
}
