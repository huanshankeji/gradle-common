# AGENTS.md

Instructions for AI coding agents working in **Huanshankeji Gradle Common** (`gradle-common`).

## Organization standards

Read first: organization-wide standards and open-source library map in [@huanshankeji/.github general agent instructions](https://github.com/huanshankeji/.github/blob/main/docs/general-agent-instructions.md). The sections below are project-specific instructions for this repository.

## Project overview

This repository publishes Kotlin Gradle convention plugins and a shared dependency catalog for Huanshankeji Kotlin projects. Plugins are implemented as precompiled script plugins (`.gradle.kts` files under each module's `src/main/kotlin/`) plus supporting Kotlin helpers.

The APIs are experimental and may change. There are no end-user tutorials here; see [README.md](README.md) for status, version notes, and links to [API docs](https://huanshankeji.github.io/gradle-common/) and the [kotlin-common](https://github.com/huanshankeji/kotlin-common) example builds.

**Toolchain:** JDK **17** (toolchain; CI uses Temurin 17 —
see [.github/workflows/ci.yml](.github/workflows/ci.yml)). Gradle
version: [gradle/wrapper/gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties). Build-logic Kotlin
version: [buildSrc/settings.gradle.kts](buildSrc/settings.gradle.kts) (`plugins { kotlin("jvm") … apply false }`); keep
in sync with [CommonVersions.kt](common-gradle-dependencies/src/main/kotlin/com/huanshankeji/CommonVersions.kt) —
see [README.md](README.md) ("Build-logic Kotlin vs Gradle's embedded Kotlin"). Release version constants for this
repository's build (`alignedPluginBaseVersion`,
`commonGradleDependenciesBaseVersion`, …): [VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt)
in `buildSrc` (build logic on the classpath when evaluating root build scripts; not published). Prefer
`providers.devCommitOrReleaseVersionProvider(baseVersion, isRelease)` (`isRelease = false` → `*-dev-commit-*`; set
`isRelease = true` on the `release` branch for the base version). Dependency versions published for downstream
projects: [CommonVersions.kt](common-gradle-dependencies/src/main/kotlin/com/huanshankeji/CommonVersions.kt) in
`common-gradle-dependencies`.

## Repository layout

| Path                                         | Gradle project name                                    | Purpose                                                                                       |
|----------------------------------------------|--------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| `buildSrc/`                                  | —                                                      | Shared build logic: versions, conventions, plugin registration helpers                        |
| `kotlin-common/gradle-library/`              | `:kotlin-common:kotlin-common-gradle-library`          | Shared helpers for kotlin-common modules                                                      |
| `kotlin-common/project-gradle-plugins/`      | `:kotlin-common:kotlin-common-project-gradle-plugins`  | General Kotlin/KMP, publishing, Dokka, benchmark, and JVM test plugins (`com.huanshankeji.*`) |
| `kotlin-common/settings-gradle-plugins/`     | `:kotlin-common:kotlin-common-settings-gradle-plugins` | Thin settings plugins (`com.huanshankeji.*` settings conventions)                             |
| `architecture-common-gradle-plugins/`        | `:architecture-common-gradle-plugins`                  | Compose/web, Vert.x, and JVM feature-variant plugins                                          |
| `common-gradle-dependencies/`                | `:common-gradle-dependencies`                          | Centralized dependency versions and helpers; published separately                             |
| `huanshankeji-team/gradle-library/`          | `:huanshankeji-team:gradle-library`                    | Shared helpers for team modules                                                               |
| `huanshankeji-team/project-gradle-plugins/`  | `:huanshankeji-team:project-gradle-plugins`            | Team-internal plugins (`com.huanshankeji.team.*`); used by `buildSrc` bootstrapping           |
| `huanshankeji-team/settings-gradle-plugins/` | `:huanshankeji-team:settings-gradle-plugins`           | Team settings plugins (`com.huanshankeji.team.*` settings conventions)                        |

Root [settings.gradle.kts](settings.gradle.kts) includes all modules. `kotlin-common` subprojects use concatenated project names (CPN); `huanshankeji-team` keeps nested simple names. Version constants live in [buildSrc/src/main/kotlin/VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt).

### Adding or changing a plugin

1. Add or edit a `*.gradle.kts` script under the target module's `src/main/kotlin/com/huanshankeji/...` (or `*.settings.gradle.kts` for settings plugins in `kotlin-common/settings-gradle-plugins` / `huanshankeji-team/settings-gradle-plugins`).
2. Register it in that module's `build.gradle.kts` (e.g., [kotlin-common/project-gradle-plugins/build.gradle.kts](kotlin-common/project-gradle-plugins/build.gradle.kts)) via `gradlePlugin { plugins { scriptPlugin(...) } }`.
3. Reuse helpers from existing Kotlin sources in the same module; prefer extending conventions rather than duplicating logic.
4. If the change affects public ABI, update the corresponding `api/*.api` dump (see below).

## Build and verification

Always use the Gradle wrapper from the repo root:

```bash
./gradlew check
```

`check` runs compilation, plugin validation, unit tests, and binary-compatibility checks. This is what CI runs.

Other useful tasks:

```bash
# Publish all plugin modules to the local Maven repo for downstream testing
./gradlew publishToMavenLocal

# Run tests for one module
./gradlew :kotlin-common:kotlin-common-project-gradle-plugins:test

# Regenerate public API dumps after intentional ABI changes
./gradlew apiCheck          # verify
./gradlew updateKotlinAbi   # update dumps (use only when ABI change is intended)
```

Configuration cache is enabled ([gradle.properties](gradle.properties)). Expect first builds to be slower.

### Bootstrap / dependency resolution

- There are no longer cross-version bootstrapping dependencies on released artifacts of this repository (#54):
    - `buildSrc` is a **multi-project build** whose subprojects ([buildSrc/settings.gradle.kts](buildSrc/settings.gradle.kts)) source-link the corresponding root modules' sources, compiling the build logic from the current source instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`. Settings plugin modules are not source-linked; the root build applies the Foojay resolver convention directly in [settings.gradle.kts](settings.gradle.kts). `buildSrc` mirrors the root `kotlin-common/` and `huanshankeji-team/` directory nesting. Kotlin-common uses CPN child project names under its parent (see the comment in [buildSrc/settings.gradle.kts](buildSrc/settings.gradle.kts)); `huanshankeji-team` uses simple child names under `:huanshankeji-team:*`. The `buildSrc` root project depends on the `huanshankeji-team:project-gradle-plugins` subproject so its `conventions` plugin can dogfood the team plugins by id.
    - Each source-linking subproject applies the Kotlin plugin via `kotlin("jvm")` (no version; registered in [buildSrc/settings.gradle.kts](buildSrc/settings.gradle.kts) with `kotlin("jvm") version … apply false`) and configures its source directory before applying `kotlin-dsl` imperatively, working around [gradle/gradle#21052](https://github.com/gradle/gradle/issues/21052).
    - Build-logic Kotlin is pinned in `buildSrc/settings.gradle.kts`, not by repeating `version.ref = "kotlin"` on `org.jetbrains.kotlin:*` [libraries] entries in the catalog (those omit a version and align via the Kotlin Gradle plugin BOM once the settings `plugins {}` block registers it). Do not rely on `pluginManagement { plugins { … } }` in `buildSrc/settings.gradle.kts` alone — see [README.md](README.md).
    - The plugin modules depend on the `common-gradle-dependencies` **project** directly (see [project-gradle-plugins-conventions.gradle.kts](buildSrc/src/main/kotlin/project-gradle-plugins-conventions.gradle.kts)), so nothing needs to be published to Maven local before building.
- Dependency versions/coordinates used by the build scripts are centralized in the shared version catalog [gradle/libs.versions.toml](gradle/libs.versions.toml), consumed by both the root build and `buildSrc` (registered in [buildSrc/settings.gradle.kts](buildSrc/settings.gradle.kts)). Keep the overlapping versions in sync with `com.huanshankeji.CommonVersions` until #9 unifies them.

- `mavenLocal()` is enabled in several build scripts for local iteration.
- Do not commit credentials or tokens.

## Code conventions

- **Kotlin style:** Follow [our Kotlin code style guide](https://github.com/huanshankeji/.github/blob/main/docs/kotlin-code-style.md) for Kotlin source in this repository.
- **Language:** Kotlin for plugin logic and helpers; Gradle Kotlin DSL for build scripts.
- **Package:** `com.huanshankeji` for published plugins; `com.huanshankeji.team` for team-only plugins.
- **Plugin IDs:** `com.huanshankeji.<kebab-case-suffix>` or `com.huanshankeji.team.<suffix>`, registered in each module's `gradlePlugin` block.
- **Naming:** kebab-case for plugin id suffixes and script file names; camelCase for Kotlin APIs. Match patterns in existing plugins and helpers in the same module.
- **Internal API:** APIs meant for in-repo use are marked
  `@GradleCommonInternalApi` ([GradleCommonInternalApi.kt](kotlin-common/gradle-library/src/main/kotlin/com/huanshankeji/GradleCommonInternalApi.kt));
  plugin modules compile with `-opt-in=com.huanshankeji.GradleCommonInternalApi` and
  `-opt-in=com.huanshankeji.GradleCommonExperimentalApi` (`InternalApi` is deprecated).
- **Public ABI:** Binary compatibility is tracked via `api/*.api` files and `apiCheck` / `checkKotlinAbi`. Do not change public signatures casually; update dumps only when the ABI change is deliberate.
- **Scope:** Keep changes minimal and localized. Match existing patterns in the module you touch. Do not add unrelated refactors, comments, or tests unless they support the task.
- **Tests:** Limited coverage today (e.g. [ConcatenatedProjectNamesTest.kt](kotlin-common/project-gradle-plugins/src/test/kotlin/com/huanshankeji/ConcatenatedProjectNamesTest.kt)). Add tests when changing non-trivial logic; run `./gradlew check` before finishing.

## Version and changelog policy

- Plugin release version: `alignedPluginBaseVersion`
  in [VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt). Prefer
  `providers.devCommitOrReleaseVersionProvider(baseVersion, isRelease)` (`isRelease = false` → `*-dev-commit-*`;
  `true` → base version on `release`).
- `common-gradle-dependencies` version: `commonGradleDependenciesBaseVersion` (separate version; releases are
  coordinated but version numbers remain independent). Same `devCommitOrReleaseVersionProvider` / `isRelease` convention
  as plugins.
- Release branch: `release` publishes to the Gradle Plugin Portal (`publishPlugins`); all other branches publish to GitHub Packages.
- Release notes: [CHANGELOG.md](CHANGELOG.md) (single change log for all published artifacts going forward).
- Outdated per-artifact change logs (historical releases only): [PLUGINS_CHANGELOG.md](PLUGINS_CHANGELOG.md), [COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md).

When bumping dependency versions, update `CommonVersions` (and `gradle/libs.versions.toml` where applicable) and note the change in [CHANGELOG.md](CHANGELOG.md) if the release is user-visible.

## IDE notes

IntelliJ IDEA is the recommended IDE ([CONTRIBUTING.md](CONTRIBUTING.md)). Set Project SDK and Gradle JVM to JDK 17.

## Pull requests and commits

Follow [CONTRIBUTING.md](CONTRIBUTING.md): discuss larger changes via issues or Discussions when appropriate.

Before opening or updating a PR:

1. Run `./gradlew check` and ensure it passes.
2. Keep commits focused with clear messages.
3. Update changelogs when the change affects published artifacts or dependency versions.
4. Do not commit secrets, GitHub/GitLab tokens, or local-only `mavenLocal` bootstrap hacks.

## Boundaries

- Do not modify generated `api/*.api` files unless the public ABI change is intentional (then run `updateKotlinAbi` and explain in the PR).
- Do not publish to remote registries (Maven Central, GitHub Packages, Plugin Portal) unless explicitly asked.
- Do not upgrade Gradle, Kotlin, or major plugin dependencies without a clear reason; version pins are tested together and documented in README.
- Prefer copying/adapting plugin patterns from this repo or [kotlin-common](https://github.com/huanshankeji/kotlin-common) over inventing new conventions.
