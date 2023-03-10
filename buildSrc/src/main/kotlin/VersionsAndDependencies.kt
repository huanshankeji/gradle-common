import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

val kotlinVersion = "1.8.10" // for Compose 1.3.1

val alignedPluginVersion = "0.4.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.5.0-20230310"

val pluginProjectDependentStableCommonGradleDependenciesVersion = "0.4.0-20230223".apply {
    require(!endsWith("SNAPSHOT"))
}
