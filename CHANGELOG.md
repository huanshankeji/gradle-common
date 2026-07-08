# Change log

## Unreleased

* overhaul module hierarchy into nested `kotlin-common/` and `huanshankeji-team/` directories with shared `gradle-library` modules
    * split build-logic conventions into `gradle-library-conventions`, `project-gradle-plugins-conventions`, `settings-gradle-plugins-conventions`, `kotlin-common-module-conventions`, and `huanshankeji-team-module-conventions`
    * extract shared helpers (`VersionRegexes`, GitHub Packages credentials, team `Constants`, `MavenRepositoryContentFiltering`, …) into `gradle-library` modules
    * `kotlin-common` subprojects use concatenated project names (CPN); `huanshankeji-team` keeps nested simple names
    * `buildSrc` no longer source-links settings plugin modules; the root build keeps the Foojay resolver convention applied directly
* replace kotlinx `binary-compatibility-validator` with Kotlin Gradle plugin `abiValidation()` on `:kotlin-common-project-gradle-plugins` and `:architecture-common-gradle-plugins` only (same scope as root `build.gradle.kts` on `main`)
* replace `com.huanshankeji.team.github-packages-maven-publish` and `com.huanshankeji.team.default-github-packages-maven-publish` with `com.huanshankeji.team.github.packages.maven.publish`
* unify release publishing on the `release` branch (replacing `plugins-release` and `common-gradle-dependencies-release`)
* remove duplicated build logic now that `buildSrc` source-links the plugin module sources (#54, #60)
    * drop the inlined `GitVersion.kt`, `kotlin-abi-validation`, and `dokka-convention` copies from `buildSrc`
    * dogfood `com.huanshankeji.git-version` and `com.huanshankeji.team.dokka.github-dokka-convention` instead
* remove the cross-version bootstrapping self-dependencies (#54)
    * `buildSrc` is now a multi-project build whose subprojects source-link the corresponding root modules' sources (`common-gradle-dependencies`, `kotlin-common/project-gradle-plugins`, `huanshankeji-team/project-gradle-plugins`) instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`
    * the plugin modules now depend on the `common-gradle-dependencies` project directly instead of a stale released version
    * dependency versions/coordinates used by the build scripts are centralized in a single shared version catalog `gradle/libs.versions.toml`, shared by both the root build and `buildSrc`
    * stop generating `GeneratedVersions` from `buildSrc`'s `DependencyVersions`; the versions are now declared directly in `CommonVersions` and kept in sync by hand with the shared version catalog (to be unified by #9)
* update Gradle to 9.6.0
* bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic
* add `com.huanshankeji.base-settings-conventions` settings plugin (Foojay toolchain resolver convention) in `:kotlin-common-settings-gradle-plugins`
* add `com.huanshankeji.team.gitversioning.public-open-source-dependency-repositories` settings plugin in `:huanshankeji-team:settings-gradle-plugins`
* split settings plugins into thin modules (`kotlin-common-settings-gradle-plugins`, `huanshankeji-team:settings-gradle-plugins`) so consumers are not forced to pull project-plugin runtime classpaths
* extract `setProjectConcatenatedNames` settings helpers alongside existing concatenated project name path APIs

## Outdated historical per-artifact change logs

Releases before the merged change log are documented separately by artifact:

* [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
* [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)
