# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- `com.huanshankeji.web-frontend-conventions` and `com.huanshankeji.material-web-frontend-conventions` (renamed from the `default-*` plugin IDs; `conventions` already implies the default)
- Experimental git-versioning / exclusive-content Maven repository / Dokka source-link APIs: `ProviderFactory` Git helpers (`gitCommitHash`, `devCommitVersionProvider`, …), `devCommitOrReleaseVersionProvider(baseVersion, isRelease)`, `ConventionVersionRegexes` (incl. `forReleaseVersionRegex`), `conventionMavenRepositories` / open-source convention overloads (disjoint `exclusiveContent` filters so Maven Central does not resolve `*-dev-commit-*`), GitHub Packages and GitLab package-registry convention helpers, `isStandardReleaseVersion`, and `conventionalGitRef`
- New context-parameter Maven registry APIs under `com.huanshankeji.github.packages.maven` and `com.huanshankeji.gitlab.packageregistry.maven`
- `com.huanshankeji.gitversioning.opensourceconvention.githubpackages.publish`: GitHub Packages + Maven Central open-source publish; call required `gitVersioningOpenSourceConventionGithubPackagesPublish.signAllPublicationsIfRelease(isRelease)` to enable signing on release (both destinations stay configured; pick the publish task for the intended destination)
- Thin settings-plugin module `kotlin-common-settings-gradle-plugins` so consumers are not forced to pull project-plugin runtime classpaths; `com.huanshankeji.base-settings-conventions` (Foojay toolchain resolver) and `setProjectConcatenatedNames`

### Changed

- **Breaking:** rename the `defaultWebFrontendConventions` extension to `webFrontendConventions` (no alias)
- **Breaking:** build-logic module overhaul: nested `kotlin-common/` with shared `gradle-library` modules; `kotlin-common` subprojects use concatenated project names (CPN); updated published artifact coordinates
- Adapt this changelog to [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) 1.1.0 (#73)
- Update Gradle to 9.6.1
- Bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic

### Deprecated

- `com.huanshankeji.default-web-frontend-conventions` (use `com.huanshankeji.web-frontend-conventions`)
- `com.huanshankeji.default-material-web-frontend-conventions` (use `com.huanshankeji.material-web-frontend-conventions`)
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

## Outdated historical per-artifact change logs

Releases before the merged changelog are documented separately by artifact:

- [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
- [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)

[Unreleased]: https://github.com/huanshankeji/gradle-common/compare/plugins-v0.11.0...HEAD
