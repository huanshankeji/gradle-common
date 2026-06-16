# AGENTS.md

Instructions for AI coding agents working in **Huanshankeji Gradle Common** (`gradle-common`).

## Project overview

This repository publishes Kotlin Gradle convention plugins and a shared dependency catalog for Huanshankeji Kotlin projects. Plugins are implemented as precompiled script plugins (`.gradle.kts` files under each module's `src/main/kotlin/`) plus supporting Kotlin helpers.

The APIs are experimental and may change. There are no end-user tutorials here; see [README.md](README.md) for status, version notes, and links to [API docs](https://huanshankeji.github.io/gradle-common/) and the [kotlin-common](https://github.com/huanshankeji/kotlin-common) example builds.

**Toolchain:** Gradle **9.4.1** (see [gradle/wrapper/gradle-wrapper.properties](gradle/wrapper/gradle-wrapper.properties)), Kotlin **2.3.20**, **JDK 17** (toolchain). CI uses Temurin 17 (see [.github/workflows/kotlin-jvm-ci.yml](.github/workflows/kotlin-jvm-ci.yml)).

## Repository layout

| Path | Gradle project name | Purpose |
|------|---------------------|---------|
| `buildSrc/` | — | Shared build logic: versions, conventions, plugin registration helpers |
| `kotlin-common-gradle-plugins/` | `:kotlin-common-gradle-plugins` | General Kotlin/KMP, publishing, Dokka, benchmark, and JVM test plugins (`com.huanshankeji.*`) |
| `architecture-common-gradle-plugins/` | `:architecture-common-gradle-plugins` | Compose/web, Vert.x, and JVM feature-variant plugins |
| `common-gradle-dependencies/` | `:common-gradle-dependencies` | Centralized dependency versions and helpers; published separately with its own changelog |
| `huanshankeji-team-gradle-plugins/` | `:gradle-plugins` | Team-internal plugins (`com.huanshankeji.team.*`); used by `buildSrc` bootstrapping |

Root [settings.gradle.kts](settings.gradle.kts) includes all modules. Version constants live in [buildSrc/src/main/kotlin/VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt).

### Adding or changing a plugin

1. Add or edit a `*.gradle.kts` script under the target module's `src/main/kotlin/com/huanshankeji/...`.
2. Register it in that module's `build.gradle.kts` (e.g., [kotlin-common-gradle-plugins/build.gradle.kts](kotlin-common-gradle-plugins/build.gradle.kts)) via `gradlePlugin { plugins { scriptConventionsPlugin(...) } }`.
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
./gradlew :kotlin-common-gradle-plugins:test

# Regenerate public API dumps after intentional ABI changes
./gradlew apiCheck          # verify
./gradlew updateKotlinAbi   # update dumps (use only when ABI change is intended)
```

Configuration cache is enabled ([gradle.properties](gradle.properties)). Expect first builds to be slower.

### Bootstrap / dependency resolution

- Plugin modules depend on a published `com.huanshankeji:common-gradle-dependencies` artifact (version in [VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt)). On branches where that artifact is unavailable, publish it locally first:

  ```bash
  ./gradlew :common-gradle-dependencies:publishToMavenLocal
  ```

- `buildSrc` bootstraps from `com.huanshankeji.team:gradle-plugins` (see [buildSrc/build.gradle.kts](buildSrc/build.gradle.kts)). Local changes to team plugins may require `publishToMavenLocal` before other modules see them.

- `mavenLocal()` is enabled in several build scripts for local iteration; do not commit credentials or tokens.

## Code conventions

- **Kotlin style:** Follow [our Kotlin code style guide](https://github.com/huanshankeji/.github/blob/main/docs/kotlin-code-style.md) for Kotlin source in this repository.
- **Language:** Kotlin for plugin logic and helpers; Gradle Kotlin DSL for build scripts.
- **Package:** `com.huanshankeji` for published plugins; `com.huanshankeji.team` for team-only plugins.
- **Plugin IDs:** `com.huanshankeji.<kebab-case-suffix>` or `com.huanshankeji.team.<suffix>`, registered in each module's `gradlePlugin` block.
- **Naming:** kebab-case for plugin id suffixes and script file names; camelCase for Kotlin APIs. See [NamingConventions.kt](kotlin-common-gradle-plugins/src/main/kotlin/com/huanshankeji/NamingConventions.kt).
- **Internal API:** APIs meant for in-repo use are marked `@InternalApi` ([Internal.kt](kotlin-common-gradle-plugins/src/main/kotlin/com/huanshankeji/Internal.kt)); plugin modules compile with `-opt-in=com.huanshankeji.InternalApi`.
- **Public ABI:** Binary compatibility is tracked via `api/*.api` files and `apiCheck` / `checkKotlinAbi`. Do not change public signatures casually; update dumps only when the ABI change is deliberate.
- **Scope:** Keep changes minimal and localized. Match existing patterns in the module you touch. Do not add unrelated refactors, comments, or tests unless they support the task.
- **Tests:** Limited coverage today (e.g. [ConcatenatedProjectNamesTest.kt](kotlin-common-gradle-plugins/src/test/kotlin/com/huanshankeji/ConcatenatedProjectNamesTest.kt)). Add tests when changing non-trivial logic; run `./gradlew check` before finishing.

## Version and changelog policy

- Plugin release version: `alignedPluginVersion` in [VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt).
- `common-gradle-dependencies` version: `commonGradleDependenciesVersion` (separate release cadence).
- User-facing plugin changes: [PLUGINS_CHANGELOG.md](PLUGINS_CHANGELOG.md).
- Dependency-catalog changes: [COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md).

When bumping dependency versions, update `DependencyVersions` / `CommonVersions` as appropriate and note the change in the relevant changelog if the release is user-visible.

## IDE notes

IntelliJ IDEA is the recommended IDE ([CONTRIBUTING.md](CONTRIBUTING.md)). Set Project SDK and Gradle JVM to JDK 17.

Script plugins in project sources sometimes fail to resolve in the IDE until a restart—this is a known limitation mentioned in [README.md](README.md).

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
