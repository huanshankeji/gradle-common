# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Settings plugins in thin modules (`kotlin-common-settings-gradle-plugins`, `huanshankeji-team:settings-gradle-plugins`) so consumers are not forced to pull project-plugin runtime classpaths
- `com.huanshankeji.base-settings-conventions` settings plugin (Foojay toolchain resolver convention) and `setProjectConcatenatedNames` settings helpers
- Shared helpers (`VersionRegexes`, GitHub Packages credentials, team `Constants`, `MavenRepositoryContentFiltering`, …) extracted into `gradle-library` modules
- `ConventionVersionRegexes` (incl. `forReleaseVersionRegex`) to configure exclusive-content version regexes on convention and open-source convention Maven repository APIs (defaults unchanged)
- `isReleaseVersion` and `conventionalGitCommitHashOrTag` (release → `v$version`, else commit hash) for Dokka source links

### Changed

- **Breaking:** build-logic module overhaul: reorganize into nested `kotlin-common/` and `huanshankeji-team/` directories with shared `gradle-library` modules, and update the published plugin/library artifact coordinates accordingly
  - Split conventions into `gradle-library-conventions`, `project-gradle-plugins-conventions`, `settings-gradle-plugins-conventions`, `kotlin-common-module-conventions`, and `huanshankeji-team-module-conventions`
  - `kotlin-common` subprojects use concatenated project names (CPN); `huanshankeji-team` keeps nested simple names
- **Breaking (experimental):** git versioning / Maven exclusive-content / Dokka source-link APIs
  - Move Git helpers and version providers from `Project` to `ProviderFactory` receivers (`gitCommitHash`, `devCommitVersionProvider`, `projectVersionFromGitProvider`, …); rename file facades `GitVersioningKt` → `DevCommitVersionKt` and `VersionKt` → `VersionMatchingKt` under `gitversioning`
  - Prefer `devCommitVersionProvider` on non-release branches; set the base version explicitly on `release`
  - `defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories` takes a literal Maven `group` instead of `groupRegex`
  - Rename `githubDokkaConvention.commitOrTag` → `commitHashOrTag`
- Replace the `com.huanshankeji.team.github-packages-maven-publish` and `com.huanshankeji.team.default-github-packages-maven-publish` plugins with `com.huanshankeji.team.github.packages.maven.publish`
- Replace kotlinx `binary-compatibility-validator` with Kotlin Gradle plugin `abiValidation()` on `:kotlin-common-project-gradle-plugins` and `:architecture-common-gradle-plugins` only (same scope as root `build.gradle.kts` on `main`)
- Unify release publishing on the `release` branch (replacing `plugins-release` and `common-gradle-dependencies-release`)
- Dogfood `com.huanshankeji.git-version` and `com.huanshankeji.team.dokka.github-dokka-convention` from `buildSrc` instead of inlined copies (#54, #60)
- Remove the cross-version bootstrapping self-dependencies (#54)
  - `buildSrc` is now a multi-project build whose subprojects source-link the corresponding root modules' sources (`common-gradle-dependencies`, `kotlin-common/project-gradle-plugins`, `huanshankeji-team/project-gradle-plugins`) instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`
  - The plugin modules now depend on the `common-gradle-dependencies` project directly instead of a stale released version
  - Dependency versions/coordinates used by the build scripts are centralized in a single shared version catalog `gradle/libs.versions.toml`, shared by both the root build and `buildSrc`
  - Stop generating `GeneratedVersions` from `buildSrc`'s `DependencyVersions`; the versions are now declared directly in `CommonVersions` and kept in sync by hand with the shared version catalog (to be unified by #9)
- Update Gradle to 9.6.0
- Bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic

### Removed

- Unused `DEV_COMMIT_AND_RELEASE_VERSION_REGEX` and the `Project`-receiver `isDevCommitVersion` / `isDirtyDevCommitVersion` overloads (keep the `String` predicates)
- Inlined `GitVersion.kt`, `kotlin-abi-validation`, and `dokka-convention` copies from `buildSrc` now that `buildSrc` source-links the plugin module sources (#54, #60)

### Fixed

- Exclusive-content version partitioning: split into multiple `exclusiveContent` blocks with disjoint `includeVersionByRegex` filters (filter lambdas now take a `versionRegex`) so Gradle no longer OR's the includes and Maven Central stops resolving `*-dev-commit-*`; the `conventionMavenRepositories` remote side is clean `*-dev-commit-*` + releases

## Outdated historical per-artifact change logs

Releases before the merged changelog are documented separately by artifact:

- [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
- [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)

[Unreleased]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.11.0...HEAD
