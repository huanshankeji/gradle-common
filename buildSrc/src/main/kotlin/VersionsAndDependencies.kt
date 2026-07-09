import com.huanshankeji.CommonGradleClasspathDependencies
import com.huanshankeji.CommonVersions

// Available on the root build classpath via buildSrc. Prefer these over duplicating
// overlapping dependency versions in `gradle/libs.versions.toml`.
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)

// Only this repository's release version constants remain here (not dependency versions).

val alignedPluginBaseVersion = "0.12.0"

// "x.y.z-yyyyMMdd" — semantic org version and date when dependency versions were updated.
// Dev-commit suffixes are applied in :common-gradle-dependencies (see its build.gradle.kts).
val commonGradleDependenciesBaseVersion = "0.10.0-20251224"
