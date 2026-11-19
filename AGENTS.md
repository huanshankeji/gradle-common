# Repository Guidelines

Before working in this repository, **load and read** the [Huanshankeji general agent instructions](https://github.com/huanshankeji/.github/blob/main/docs/general-agent-instructions.md) as the required organization-wide baseline. A skipped or failed load, or an unread document, is a blocking error until the document is in context and has been read.

## Project Structure

This repository publishes experimental Kotlin Gradle convention plugins and shared dependency helpers. See [README.md](README.md#project-status-and-guide) for project status, [API documentation](https://huanshankeji.github.io/gradle-common/), and example builds in [kotlin-common](https://github.com/huanshankeji/kotlin-common).

The [root settings file](settings.gradle.kts) defines the Gradle project paths. `kotlin-common` uses concatenated names for its child projects. `huanshankeji-team` uses simple names under its parent project.

| Path | Gradle project | Purpose |
| --- | --- | --- |
| `buildSrc/` | Separate build | Build conventions, version constants, and plugin registration helpers |
| `kotlin-common/gradle-library/` | `:kotlin-common:kotlin-common-gradle-library` | Shared Gradle helpers |
| `kotlin-common/project-gradle-plugins/` | `:kotlin-common:kotlin-common-project-gradle-plugins` | Kotlin, multiplatform, publishing, Dokka, benchmark, and JVM test conventions |
| `kotlin-common/settings-gradle-plugins/` | `:kotlin-common:kotlin-common-settings-gradle-plugins` | Thin Kotlin settings conventions |
| `architecture-common-gradle-plugins/` | `:architecture-common-gradle-plugins` | Compose and web frontend, Vert.x, and JVM feature-variant conventions |
| `common-gradle-dependencies/` | `:common-gradle-dependencies` | Separately published dependency coordinates, versions, and helpers |
| `huanshankeji-team/gradle-library/` | `:huanshankeji-team:gradle-library` | Shared helpers for team modules |
| `huanshankeji-team/project-gradle-plugins/` | `:huanshankeji-team:project-gradle-plugins` | Team project conventions used by `buildSrc` |
| `huanshankeji-team/settings-gradle-plugins/` | `:huanshankeji-team:settings-gradle-plugins` | Team settings conventions |

Module sources live in `src/main/kotlin/`, tests in `src/test/kotlin/`, and tracked Kotlin ABI references in `api/*.api`.

## Build and Development

Use JDK 17, matching the toolchain and [CI](.github/workflows/ci.yml), and run the Gradle wrapper from the repository root. IntelliJ IDEA is the recommended IDE. Set Project SDK to JDK 17 and use it for the Gradle JVM.

- `./gradlew build` assembles the artifacts and runs verification.
- `./gradlew check` runs tests, plugin validation, and Kotlin ABI checks.
- Run `./gradlew checkKotlinAbi` to compare the current ABI with the tracked reference snapshots. For intentional ABI changes, run `./gradlew updateKotlinAbi` and review the generated diff before including the updated snapshots. See [Kotlin’s ABI validation guide](https://kotlinlang.org/docs/gradle-binary-compatibility-validation.html).
- `./gradlew publishToMavenLocal` installs artifacts for testing in downstream builds.
- `./gradlew :dokkaGeneratePublicationHtml` generates the API documentation locally.

Keep the build-logic Kotlin pin in the [`buildSrc` settings file](buildSrc/settings.gradle.kts) in sync with [CommonVersions.kt](common-gradle-dependencies/src/main/kotlin/com/huanshankeji/CommonVersions.kt). A Kotlin version mismatch warning from `kotlin-dsl` can reflect the intentional difference between the build-logic pin and Gradle's embedded Kotlin. Check the configured combination and the [README explanation](README.md#build-logic-kotlin-vs-gradles-embedded-kotlin). Only upgrade Gradle, Kotlin, or major plugin dependencies when there is a clear reason. For Gradle and Kotlin upgrades, follow the [version-sync and downstream verification steps](README.md#kotlin-and-gradle-upgrades).

Configuration cache is enabled in [gradle.properties](gradle.properties). Expect the first build to take longer.

### Build-logic bootstrap

- `buildSrc` mirrors the relevant root modules and compiles their source directories directly. Its root build depends on the source-linked team project plugins so its conventions can apply them by ID. This setup removes the need to publish this repository's artifacts to Maven Local to bootstrap the build. The root settings apply the Foojay resolver directly, and settings-plugin modules are built by the root build.
- Source-linked projects apply the Kotlin JVM plugin registered in `buildSrc/settings.gradle.kts`. In modules with precompiled script plugins, configure the linked source directories before applying `kotlin-dsl` imperatively. This keeps the linked scripts visible during accessor generation and works around [Gradle #21052](https://github.com/gradle/gradle/issues/21052).
- Keep the Kotlin JVM plugin pinned in the top-level settings `plugins` block with `apply false`. That registration puts the Kotlin Gradle plugin BOM on the build classpath to align versionless `org.jetbrains.kotlin` dependencies.
- The root build and `buildSrc` share [gradle/libs.versions.toml](gradle/libs.versions.toml). Keep overlapping versions in sync with `CommonVersions` while both are in use. Project plugin modules depend directly on `:common-gradle-dependencies` through [their build conventions](buildSrc/src/main/kotlin/project-gradle-plugins-conventions.gradle.kts).

## Coding Style and Naming

Follow the [organization's Kotlin style guide](https://github.com/huanshankeji/.github/blob/main/docs/kotlin-code-style.md). Use four-space indentation, `UpperCamelCase` for types, and `lowerCamelCase` for functions and properties. Use Kotlin for plugin logic and helpers and Gradle Kotlin DSL for build scripts. Use `com.huanshankeji` for public plugins and `com.huanshankeji.team` for team-only plugins. Use kebab-case for plugin ID suffixes and script filenames.

Use modern Gradle APIs and follow the [Gradle best practices](https://docs.gradle.org/current/userguide/best_practices_index.html) that apply to the change, including lazy `Provider` and `Property` wiring. If a broader migration is needed, defer the part outside the current change. Keep changes focused and reuse nearby helpers. Look to existing plugins here and the kotlin-common builds for convention patterns.

Prefer function APIs on `ProviderFactory` or another existing receiver. Add a plugin when the feature needs one, when a plugin substantially simplifies the implementation, or when a plugin is the conventional Gradle API for the feature. Put precompiled project plugins in `*.gradle.kts` files in the target module's package under `src/main/kotlin/`. Use `*.settings.gradle.kts` in the settings-plugin modules. Register published plugins via `scriptPlugin(...)` in the module's `gradlePlugin` block, following [the Kotlin project plugins](kotlin-common/project-gradle-plugins/build.gradle.kts).

Mark APIs intended for internal use with [`@GradleCommonInternalApi`](kotlin-common/gradle-library/src/main/kotlin/com/huanshankeji/GradleCommonInternalApi.kt). Plugin build conventions opt into `GradleCommonInternalApi` and `GradleCommonExperimentalApi`. `InternalApi` is deprecated.

## Testing

Coverage is limited. Add tests when changing non-trivial logic. Use `kotlin.test` with `@Test`, `*Test.kt` filenames, and descriptive test names. Run a focused suite with `./gradlew :kotlin-common:kotlin-common-gradle-library:test`, which includes [ConcatenatedProjectNamesTest.kt](kotlin-common/gradle-library/src/test/kotlin/com/huanshankeji/ConcatenatedProjectNamesTest.kt). Run `./gradlew check` before opening or updating a PR.

Use the affected plugin module's `validatePlugins` task to verify task property annotations.

## Versioning and Publishing

[VersionsAndDependencies.kt](buildSrc/src/main/kotlin/VersionsAndDependencies.kt) defines `alignedPluginBaseVersion` for the plugins and `commonGradleDependenciesBaseVersion` for `common-gradle-dependencies`. Their releases are coordinated and their version numbers are independent. Use `providers.devCommitOrReleaseVersionProvider(baseVersion, isRelease)` for version selection. Use `isRelease = true` for release versions on `release` and `false` for development versions of the form `*-dev-commit-*`.

When bumping dependencies, update `CommonVersions` and the version catalog where applicable. Record user-visible changes to published artifacts or dependency versions under `Unreleased` in [CHANGELOG.md](CHANGELOG.md). [PLUGINS_CHANGELOG.md](PLUGINS_CHANGELOG.md) and [COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) cover historical releases.

The [publish workflow](.github/workflows/publish.yml) runs `publishPlugins` for the Gradle Plugin Portal on `release` and publishes to GitHub Packages on other branches. Publish to remote registries only when explicitly asked. Use `publishToMavenLocal` for local downstream testing, where the repository conventions can resolve the artifacts from Maven Local.

## Commits and Pull Requests

Keep commits focused and use imperative subjects such as `Rename buildSrc common-conventions to base-conventions`. Keep credentials and machine-specific Maven Local bootstrap workarounds out of commits. In the PR, explain the change and its motivation, report validation, and link any related issue or discussion. Explain any intentional ABI changes. Discuss larger changes in an issue or GitHub Discussion when appropriate. See [CONTRIBUTING.md](CONTRIBUTING.md) for the contribution workflow.
