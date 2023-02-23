import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

val kotlinVersion = "1.8.0" // for Compose 1.3.0

val alignedPluginVersion = "0.4.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.4.0-20230223-SNAPSHOT"

val pluginProjectDependentStableCommonGradleDependenciesVersion = "0.3.2-20220728"
