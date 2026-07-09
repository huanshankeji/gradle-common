/*
// Bootstrapping from "common-gradle-dependencies"
val commonVersions = CommonVersions()
val commonGradleClasspathDependencies = CommonGradleClasspathDependencies(commonVersions)
*/


// Dependency versions/coordinates needed to compile the plugin sources live in the
// shared version catalog `gradle/libs.versions.toml` (#54). Only this repository's
// release version constants remain here.
//
// Overlaps with `CommonVersions` (kotlin / compose / kotlinx-benchmark) stay duplicated
// for now: Gradle's `version-catalog` plugin publishes a TOML for settings-time `from()`,
// which does not feed Kotlin default-parameter constants in a published library. Unifying
// that is #9 (replace / complement `common-gradle-dependencies` with a published catalog).

val alignedPluginBaseVersion = "0.12.0"

// "x.y.z-yyyyMMdd" — semantic org version and date when dependency versions were updated.
// Dev-commit suffixes are applied in :common-gradle-dependencies (see its build.gradle.kts).
val commonGradleDependenciesBaseVersion = "0.10.0-20251224"
