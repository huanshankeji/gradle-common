# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

This repository publishes two version series. Plugin modules share one
`MAJOR.MINOR.PATCH` version. `common-gradle-dependencies` uses a separate
`MAJOR.MINOR.PATCH-YYYYMMDD` version. Team-only and build-logic notes use extra
`Team` and `Internal` groupings after the standard change types.

## [Unreleased]

### Added

- `com.huanshankeji.web-frontend-conventions` (renamed from `com.huanshankeji.default-web-frontend-conventions`; `conventions` already implies the default)
- Experimental git-versioning / exclusive-content Maven repository / Dokka source-link APIs: `ProviderFactory` Git helpers (`gitCommitHash`, `devCommitVersionProvider`, …), `devCommitOrReleaseVersionProvider(baseVersion, isRelease)`, `ConventionVersionRegexes` (incl. `forReleaseVersionRegex`), `conventionMavenRepositories` / open-source convention overloads (disjoint `exclusiveContent` filters so Maven Central does not resolve `*-dev-commit-*`), GitHub Packages and GitLab package-registry convention helpers, `isStandardReleaseVersion`, and `conventionalGitRef`
- New context-parameter Maven registry APIs under `com.huanshankeji.github.packages.maven` and `com.huanshankeji.gitlab.packageregistry.maven`
- `com.huanshankeji.gitversioning.opensourceconvention.githubpackages.publish`: GitHub Packages + Maven Central open-source publish; call required `gitVersioningOpenSourceConventionGithubPackagesPublish.signAllPublicationsIfRelease(isRelease)` to enable signing on release (both destinations stay configured; pick the publish task for the intended destination)
- Thin settings-plugin module `kotlin-common-settings-gradle-plugins` so consumers are not forced to pull project-plugin runtime classpaths; `com.huanshankeji.base-settings-conventions` (Foojay toolchain resolver) and `setProjectConcatenatedNames`

### Changed

- **Breaking:** build-logic module overhaul: nested `kotlin-common/` with shared `gradle-library` modules; `kotlin-common` subprojects use concatenated project names (CPN); updated published artifact coordinates
- Update Gradle to 9.6.1
- Bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic

### Deprecated

- `com.huanshankeji.default-web-frontend-conventions` (use `com.huanshankeji.web-frontend-conventions`; the old plugin keeps its own implementation and the `defaultWebFrontendConventions` extension)
- `com.huanshankeji.default-material-web-frontend-conventions` (no longer used or needed)
- Project-receiver GitHub Packages / GitLab package-registry Maven helpers in `com.huanshankeji` (use `com.huanshankeji.github.packages.maven` / `com.huanshankeji.gitlab.packageregistry.maven`)
- Separate per-artifact changelogs ([PLUGINS_CHANGELOG.md](PLUGINS_CHANGELOG.md), [COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md)); use this file going forward

### Team (for Huanshankeji's own team)

#### Added

- `com.huanshankeji.team.gitversioning.opensourceconvention.githubpackages.publish` team defaults wrapper for the open-source GitHub Packages + Maven Central publish convention
- Thin settings-plugin module `huanshankeji-team:settings-gradle-plugins`

#### Changed

- Nested `huanshankeji-team/` layout with shared `gradle-library`; nested simple project names; `huanshankeji-team-module-conventions`
- Rename `githubDokkaConvention.commitOrTag` → `gitRef`
- Replace `com.huanshankeji.team.github-packages-maven-publish` and `com.huanshankeji.team.default-github-packages-maven-publish` with `com.huanshankeji.team.github.packages.maven.publish`

#### Deprecated

- `githubDokkaConvention.commitOrTag` (use `gitRef`)

### Internal

#### Changed

- Split build-logic conventions into `gradle-library-conventions`, `project-gradle-plugins-conventions`, `settings-gradle-plugins-conventions`, `kotlin-common-module-conventions`, and `huanshankeji-team-module-conventions`
- Replace kotlinx `binary-compatibility-validator` with Kotlin Gradle plugin `abiValidation()` via `aligned-version-build-logic-conventions` (all modules using that convention)
- Unify release publishing on the `release` branch (replacing `plugins-release` and `common-gradle-dependencies-release`)
- Remove cross-version bootstrapping self-dependencies (#54, #60): `buildSrc` is a multi-project build whose subprojects source-link the corresponding root module sources; plugin modules depend on the `common-gradle-dependencies` project; shared `gradle/libs.versions.toml`; drop inlined `buildSrc` copies (`GitVersion.kt`, `kotlin-abi-validation`, `dokka-convention`); dogfood `com.huanshankeji.team.dokka.github-dokka-convention` and call `devCommitOrReleaseVersionProvider` directly; versions live in `CommonVersions` (kept in sync with the catalog by hand until #9)
- Adapt this changelog to [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) 1.1.0, including historical plugin and `common-gradle-dependencies` releases (#64)

## [0.11.0] - 2025-10-28

Plugins.

### Added

- `maven-central-publish-conventions` plugin based on `com.vanniktech.maven.publish`
  - `MavenPom.setUpPomForTeamDefaultOpenSource` to help configure this plugin
- `inceptionYear` parameter to Maven POM configuration functions (#41)

### Deprecated

- Old OSSRH publish plugins (superseded by `maven-central-publish-conventions`)

### Fixed

- `GeneratedVersions` not generated when running `publishToMavenLocal` from a clean state (#38)

## [0.10.0] - 2025-10-25

Plugins.

### Changed

- Update Gradle to 9.1.0 and migrate to Gradle 9, fixing some incompatibilities
- Update the Java version to 17
- Bump dependencies to the latest

## [0.10.0-20251024] - 2025-10-25

`common-gradle-dependencies`.

### Changed

- Update Gradle to 9.1.0 and migrate to Gradle 9, fixing some incompatibilities
- Update the Java version to 17
- Bump dependencies to the latest

## [0.9.0] - 2024-12-05

Plugins. There are no functional changes in this release.

### Changed

- Bump the `common-gradle-dependencies` dependency to v0.9.0-20241203

## [0.9.0-20241203] - 2024-12-05

`common-gradle-dependencies`.

### Added

- A version for kotlinx-io

### Changed

- Bump the dependency versions in `CommonVersions` to the latest as of Dec 3, 2024 (including pre-release versions except for Vert.x 5)

## [0.8.0] - 2024-12-03

Plugins.

### Changed

- Bump Kotlin to 2.1.0, Gradle to v8.1.11, kotlinx-benchmark to v0.4.13, and Compose Multiplatform to v1.7.1

### Removed

- The `*-app-conventions` plugins that are not necessary and buggy

  The JVM dependency configuration line `implementation(platform(kotlin("bom")))` without a Kotlin version has introduced "Could not parse POM" build error in projects consuming the `kotlin-jvm-*-app-conventions` plugins

## [0.7.0] - 2024-11-20

Plugins.

### Added

- API documentation generated by Dokka hosted at <https://huanshankeji.github.io/gradle-common/>
- Dokka convention plugins `com.huanshankeji.dokka.dokka-convention` and `com.huanshankeji.team.dokka.github-dokka-convention` to ease Dokka configuration

### Changed

- Do not configure the signing DSL at all when the version is a snapshot in the `com.huanshankeji.sonatype-ossrh-publish` plugin, so `publishToMavenLocal` becomes significantly faster
- Do not add any Maven repositories any more in plugins

### Internal

- Add `CODE_OF_CONDUCT.md` and `CONTRIBUTING.md`
- Use the Kotlin binary compatibility validator

## [0.6.0] - 2024-10-18

Plugins.

### Added

- A `sparkJava17CompatibleJvmArgs` variable

### Changed

- Bump Kotlin to 2.0.10 and Compose Multiplatform to 1.7.0
- Refactor the `kotlin-multiplatform-jvm-and-js-browser-app-conventions` plugin into `kotlin-multiplatform-app-conventions-with-conventional-targets`

### Removed

- The bootstrapping `common-gradle-dependencies` dependency; put the shared dependencies in `buildSrc` `DependencyVersions`, and generate versions for `common-gradle-dependencies` (see caf83246808b33bbb854bc486476758c73b9f398 for more details)

### Fixed

- An error in `GenerateKotlinJsBrowserWebrootForVertxWebPlugin` that emerged with the new Kotlin version

## [0.8.0-20241016] - 2024-10-18

`common-gradle-dependencies`.

### Added

- Some dependencies and versions of `androidx` and `org.jetbrains.androidx`

### Changed

- Bump the dependency versions in `CommonVersions` to the latest compatible with Kotlin 2.0.10 as of Oct 16, 2024

Earlier tagged releases are listed on [GitHub Releases](https://github.com/huanshankeji/gradle-common/releases).
The deprecated per-artifact files ([PLUGINS_CHANGELOG.md](PLUGINS_CHANGELOG.md), [COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md)) keep copies of the notes above.

[Unreleased]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.11.0...HEAD
[0.11.0]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.10.0...plugins-v0.11.0
[0.10.0]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.9.0...plugins-v0.10.0
[0.10.0-20251024]: https://github.com/huanshankeji/gradle-common/compare/common-gradle-dependencies-v0.9.0-20241203...common-gradle-dependencies-v0.10.0-20251024
[0.9.0]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.8.0...plugins-v0.9.0
[0.9.0-20241203]: https://github.com/huanshankeji/gradle-common/compare/common-gradle-dependencies-v0.8.0-20241016...common-gradle-dependencies-v0.9.0-20241203
[0.8.0]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.7.0...plugins-v0.8.0
[0.7.0]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.6.0...plugins-v0.7.0
[0.6.0]: https://github.com/huanshankeji/gradle-common/releases/tag/plugins-v0.6.0
[0.8.0-20241016]: https://github.com/huanshankeji/gradle-common/releases/tag/common-gradle-dependencies-v0.8.0-20241016
