// Dependency versions/coordinates needed to compile the plugin sources now live in the
// shared version catalog `gradle/libs.versions.toml` (#54). Only the project release
// versions remain here, as they are not dependency versions and are read by the
// precompiled script plugins and module build scripts.

val alignedPluginVersion = "0.12.0-SNAPSHOT"

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
val commonGradleDependenciesVersion = "0.10.0-20251224-SNAPSHOT"
