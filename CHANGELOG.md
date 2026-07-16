# Change log

## Unreleased

* build-logic module overhaul: reorganize into nested `kotlin-common/` and `huanshankeji-team/` directories with shared `gradle-library` modules, and update the published plugin/library artifact coordinates accordingly
    * split conventions into `gradle-library-conventions`, `project-gradle-plugins-conventions`, `settings-gradle-plugins-conventions`, `kotlin-common-module-conventions`, and `huanshankeji-team-module-conventions`, and extract shared helpers (`VersionRegexes`, GitHub Packages credentials, team `Constants`, `MavenRepositoryContentFiltering`, …) into `gradle-library` modules
    * split settings plugins into thin modules (`kotlin-common-settings-gradle-plugins`, `huanshankeji-team:settings-gradle-plugins`) so consumers are not forced to pull project-plugin runtime classpaths; add the `com.huanshankeji.base-settings-conventions` settings plugin (Foojay toolchain resolver convention) and `setProjectConcatenatedNames` settings helpers
    * `kotlin-common` subprojects use concatenated project names (CPN); `huanshankeji-team` keeps nested simple names
* git versioning / Maven exclusive-content / Dokka source-link APIs (breaking, experimental)
    * move Git helpers and version providers from `Project` to `ProviderFactory` receivers (`gitCommitHash`, `devCommitVersionProvider`, `projectVersionFromGitProvider`, …); rename file facades `GitVersioningKt` → `DevCommitVersionKt` and `VersionKt` → `VersionMatchingKt` under `gitversioning`
    * prefer `devCommitVersionProvider` on non-release branches; set the base version explicitly on `release`
    * fix exclusive-content version partitioning: split into multiple `exclusiveContent` blocks with disjoint `includeVersionByRegex` filters (filter lambdas now take a `versionRegex`) so Gradle no longer OR's the includes and Maven Central stops resolving `*-dev-commit-*`; the `conventionMavenRepositories` remote side is clean `*-dev-commit-*` + releases
    * add `ConventionVersionRegexes` (incl. `forReleaseVersionRegex`) to configure these regexes on convention and open-source convention Maven repository APIs (defaults unchanged); `defaultHuanshankejiGitlabPackageRegistryProjectEndpointConventionMavenRepositories` takes a literal Maven `group` instead of `groupRegex`
    * add `isReleaseVersion` and `conventionalGitCommitHashOrTag` (release → `v$version`, else commit hash) for Dokka source links; rename `githubDokkaConvention.commitOrTag` → `commitHashOrTag`
    * remove unused `DEV_COMMIT_AND_RELEASE_VERSION_REGEX` and the `Project`-receiver `isDevCommitVersion` / `isDirtyDevCommitVersion` overloads (keep the `String` predicates)
* replace the `com.huanshankeji.team.github-packages-maven-publish` and `com.huanshankeji.team.default-github-packages-maven-publish` plugins with `com.huanshankeji.team.github.packages.maven.publish`
* replace kotlinx `binary-compatibility-validator` with Kotlin Gradle plugin `abiValidation()` on `:kotlin-common-project-gradle-plugins` and `:architecture-common-gradle-plugins` only (same scope as root `build.gradle.kts` on `main`)
* unify release publishing on the `release` branch (replacing `plugins-release` and `common-gradle-dependencies-release`)
* remove duplicated build logic now that `buildSrc` source-links the plugin module sources (#54, #60): drop the inlined `GitVersion.kt`, `kotlin-abi-validation`, and `dokka-convention` copies from `buildSrc` and dogfood `com.huanshankeji.git-version` and `com.huanshankeji.team.dokka.github-dokka-convention` instead
* remove the cross-version bootstrapping self-dependencies (#54)
    * `buildSrc` is now a multi-project build whose subprojects source-link the corresponding root modules' sources (`common-gradle-dependencies`, `kotlin-common/project-gradle-plugins`, `huanshankeji-team/project-gradle-plugins`) instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`
    * the plugin modules now depend on the `common-gradle-dependencies` project directly instead of a stale released version
    * dependency versions/coordinates used by the build scripts are centralized in a single shared version catalog `gradle/libs.versions.toml`, shared by both the root build and `buildSrc`
    * stop generating `GeneratedVersions` from `buildSrc`'s `DependencyVersions`; the versions are now declared directly in `CommonVersions` and kept in sync by hand with the shared version catalog (to be unified by #9)
* update Gradle to 9.6.0
* bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic

## Outdated historical per-artifact change logs

Releases before the merged change log are documented separately by artifact:

* [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
* [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)
