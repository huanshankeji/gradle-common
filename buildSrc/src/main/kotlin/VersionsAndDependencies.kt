/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


// Dependency versions/coordinates needed to compile the plugin sources live in the
// shared version catalog `gradle/libs.versions.toml`. Overlapping values used by
// `CommonVersions` are generated into `GeneratedVersions` from that catalog.
// Only this repository's release version constants remain here.

val alignedPluginBaseVersion = "0.12.0"

// "x.y.z-yyyyMMdd" — semantic org version and date when dependency versions were updated.
// Dev-commit suffixes are applied in :common-gradle-dependencies (see its build.gradle.kts).
val commonGradleDependenciesBaseVersion = "0.10.0-20251224"
