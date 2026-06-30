# Change log

## Unreleased

* unify release publishing on the `release` branch (replacing `plugins-release` and `common-gradle-dependencies-release`)
* remove duplicated build logic now that `buildSrc` source-links the plugin module sources (#54, #60)
    * drop the inlined `GitVersion.kt`, `kotlin-abi-validation`, and `dokka-convention` copies from `buildSrc`
    * dogfood `com.huanshankeji.git-version`, `com.huanshankeji.kotlin-abi-validation-conventions`, and `com.huanshankeji.team.dokka.github-dokka-convention` instead
* remove the cross-version bootstrapping self-dependencies (#54)
    * `buildSrc` is now a multi-project build whose subprojects source-link the corresponding root modules' sources (`common-gradle-dependencies`, `kotlin-common-gradle-plugins`, `huanshankeji-team-gradle-plugins`) instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`
    * the plugin modules now depend on the `common-gradle-dependencies` project directly instead of a stale released version
    * dependency versions/coordinates used by the build scripts are centralized in a single shared version catalog `gradle/libs.versions.toml`, shared by both the root build and `buildSrc`
    * stop generating `GeneratedVersions` from `buildSrc`'s `DependencyVersions`; the versions are now declared directly in `CommonVersions` and kept in sync by hand with the shared version catalog (to be unified by #9)
* update Gradle to 9.6.0
* bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic
* add `com.huanshankeji.base-settings-conventions` settings plugin (Foojay toolchain resolver convention)
* extract `setProjectConcatenatedNames` settings helpers alongside existing concatenated project name path APIs

## Outdated historical per-artifact change logs

Releases before the merged change log are documented separately by artifact:

* [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
* [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)
