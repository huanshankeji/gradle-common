# Repository Guidelines

## Project Structure

This repository publishes Kotlin Gradle convention plugins and shared dependency helpers. `settings.gradle.kts` defines the Gradle project paths.

- `kotlin-common/` contains Gradle helpers, project plugins, and settings plugins. `huanshankeji-team/` mirrors that split for team conventions.
- `architecture-common-gradle-plugins/` contains web frontend, Vert.x, and JVM feature-variant conventions. `common-gradle-dependencies/` contains dependency coordinates and versions for downstream builds.
- `buildSrc/` provides build conventions and compiles selected modules directly from their source directories. It shares `gradle/libs.versions.toml` with the root build.
- Module sources live in `src/main/kotlin/`, tests in `src/test/kotlin/`, and tracked Kotlin ABI references in `api/*.api`.

## Build and Development

Use JDK 17, matching CI, and run the Gradle wrapper from the repository root.

- `./gradlew build` assembles the artifacts and runs verification.
- `./gradlew check` runs tests, plugin validation, and Kotlin ABI checks.
- `./gradlew publishToMavenLocal` installs artifacts for testing in downstream builds.
- `./gradlew :dokkaGeneratePublicationHtml` generates the API documentation locally.

For Gradle or Kotlin upgrades, follow the version-sync and downstream verification steps in [README.md](README.md#kotlin-and-gradle-upgrades).

## Coding Style and Naming

Use four-space indentation, `UpperCamelCase` for types, and `lowerCamelCase` for functions and properties. Prefer Kotlin functions for new Gradle helpers and use lazy `Provider` and `Property` wiring where applicable. Use kebab-case plugin ID suffixes under `com.huanshankeji` or `com.huanshankeji.team`. Put precompiled plugins in `*.gradle.kts` files, use `*.settings.gradle.kts` for settings plugins, and register published plugins in the module's `gradlePlugin` block.

## Testing

Use `kotlin.test` with `@Test`, `*Test.kt` filenames, and descriptive test names. Run a focused suite with `./gradlew :kotlin-common:kotlin-common-gradle-library:test`. Run `./gradlew check` before opening a PR. For an intentional ABI change, regenerate and review the affected `api/*.api` files with `./gradlew updateKotlinAbi`.

## Commits and Pull Requests

Keep commits focused and use imperative subjects such as `Rename buildSrc common-conventions to base-conventions`. In the PR, explain the change and its motivation, report validation, and link any related issue or discussion. Record user-visible changes under `Unreleased` in [CHANGELOG.md](CHANGELOG.md). See [CONTRIBUTING.md](CONTRIBUTING.md) for the contribution workflow.
