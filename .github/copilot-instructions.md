# Copilot Instructions for gradle-common

## Repository Overview

**Huanshankeji Gradle Common** is a collection of Gradle plugins and common dependencies for Kotlin projects. The repository provides convention plugins, dependency management, and build utilities primarily used by Huanshankeji for their Kotlin projects.

**High-level Repository Information:**
- **Project Type:** Gradle plugin library with multiple modules
- **Languages:** Kotlin (primary), Gradle Kotlin DSL
- **Target Runtime:** JVM (JDK 17+, tested with JDK 17)
- **Framework:** Gradle 9.0.0, Kotlin 2.2.10
- **Size:** Medium-sized multi-module project (~20 plugin modules)
- **APIs:** Experimental and subject to change

## Build Instructions

### Prerequisites
- JDK 17 or higher (JDK 17 is used in CI)
- Gradle wrapper handles Gradle version automatically

### Essential Build Commands

**Always run commands in the repository root directory.**

#### Bootstrap and Dependencies
```bash
# No special bootstrap needed - Gradle wrapper handles dependencies
./gradlew --version  # Verify Gradle 9.0.0 is used
```

#### Build
```bash
./gradlew build     # Full build (takes ~30 seconds, includes tests)
./gradlew check     # Run checks without building distributions (~1 minute)
```

#### Test
```bash
./gradlew test      # Run all tests (minimal test suite)
```

#### Clean and Rebuild
```bash
./gradlew clean     # Clean all build outputs
./gradlew build     # Rebuild from scratch
```

#### Local Publishing
```bash
./gradlew publishToMavenLocal  # Publish to local Maven repo (~2 seconds)
```

#### Documentation Generation
```bash
./gradlew dokkaGeneratePublicationHtml  # Generate API docs with Dokka
./gradlew dokkaGenerate                  # Alternative Dokka generation command
```

#### API Validation
```bash
./gradlew apiCheck    # Check API compatibility against golden files
./gradlew apiDump     # Update API golden files for modules
./gradlew validatePlugins  # Validate plugin parameter annotations
```

### Known Build Issues and Workarounds

**Warning Messages (Safe to Ignore):**
- "Unsupported Kotlin plugin version" warnings related to Gradle Kotlin DSL compatibility
- Deprecation warnings about `buildDir` usage

**Memory Requirements:**
- JVM args set to `-Xmx2G` in `gradle.properties` for building architecture-common-gradle-plugins

**Bootstrap Dependencies:**
- If the build fails with "Could not find com.huanshankeji:common-gradle-dependencies" error, run `./gradlew :common-gradle-dependencies:publishToMavenLocal` first
- This typically happens after merging changes that update dependency versions

**Note:** The README mentions "./gradlew build needs to run twice to work" but this was not observed during testing.

## Project Layout and Architecture

### Module Structure
```
gradle-common/
├── kotlin-common-gradle-plugins/     # Core Kotlin plugin conventions
├── architecture-common-gradle-plugins/ # Web/architecture-specific plugins
├── common-gradle-dependencies/       # Shared dependency versions
├── huanshankeji-team-gradle-plugins/ # Team-specific utilities
├── buildSrc/                        # Build configuration and scripts
├── .github/workflows/               # CI/CD pipelines
└── gradle/wrapper/                  # Gradle wrapper
```

### Key Configuration Files

**Root Level:**
- `build.gradle.kts` - Root build configuration with Dokka and plugin publishing
- `settings.gradle.kts` - Module inclusion and dependency resolution
- `gradle.properties` - Build properties (JVM args, Dokka settings)

**Build Configuration:**
- `buildSrc/src/main/kotlin/VersionsAndDependencies.kt` - Centralized version management
- `buildSrc/src/main/kotlin/Constants.kt` - Project constants
- `buildSrc/build.gradle.kts` - Build dependencies and Kotlin version

**CI/CD:**
- `.github/workflows/kotlin-jvm-ci.yml` - Main CI pipeline (Ubuntu, JDK 17)
- `.github/workflows/dokka-gh-pages.yml` - Documentation deployment
- `.github/workflows/copilot-setup-steps.yml` - Setup automation

### Major Components

**kotlin-common-gradle-plugins:** Convention plugins for Kotlin projects including:
- `kotlin-jvm-library-*` plugins for JVM libraries
- `kotlin-multiplatform-*` plugins for multiplatform projects
- Maven publishing conventions
- Sonatype OSSRH publishing

**architecture-common-gradle-plugins:** Web and architecture plugins including:
- `GenerateKotlinJsBrowserWebrootForVertxWebPlugin` for Vert.x integration
- Web frontend conventions
- JavaScript browser distribution handling

**common-gradle-dependencies:** Centralized dependency versions in `CommonVersions.kt`

### Validation Pipeline

The repository uses GitHub Actions CI that:
1. Runs on JDK 17 with Temurin distribution
2. Executes `gradle-test-and-check` action
3. Performs dependency submission for security scanning
4. Generates API documentation with Dokka

**To replicate CI locally:**
```bash
./gradlew check                    # Equivalent to CI test-and-check
./gradlew publishToMavenLocal      # Verify publishing works
./gradlew apiCheck                 # Verify API compatibility
```

### Dependencies and Compatibility

**External Dependencies:**

- Kotlin Gradle Plugin 2.2.10
- Compose Multiplatform 1.10.0-alpha01
- Dokka 2.1.0
- Gradle Plugin Publish Plugin 2.0.0
- Kotlin Binary Compatibility Validator 0.18.1

**Version Compatibility Notes:**
- Gradle 9.0.0 required (handled by wrapper)
- "Unsupported Kotlin plugin version" warnings are normal when the Kotlin Gradle plugin version is newer than the version bundled in Gradle
- Compose Multiplatform versions constrain Kotlin version compatibility

### Development Guidelines

**IDE Setup (IntelliJ IDEA):**
- Set Project SDK to JDK 17 or higher
- Set Gradle JVM to "Project SDK"
- Note: IntelliJ IDEA 2024.3 has loading issues (reported as IDEA-363846)

**Plugin Development:**
- Script plugins in project sources may require IntelliJ restart for proper resolution
- Use `publishToMavenLocal` to test changes in consuming projects

**Version Management:**
- Plugin versions aligned via `alignedPluginVersion` in VersionsAndDependencies.kt
- Common dependency versions in `commonGradleDependenciesVersion`
- Snapshot versions used for development

**Code Style:**
- Follow [our Kotlin code style guide](https://github.com/huanshankeji/.github/blob/main/kotlin-code-style.md) for all Kotlin code contributions

### Key Source Files to Examine

**For Plugin Development:**
- `kotlin-common-gradle-plugins/src/main/kotlin/com/huanshankeji/` - Core plugin implementations
- `architecture-common-gradle-plugins/src/main/kotlin/com/huanshankeji/` - Architecture plugins

**For Version Management:**
- `common-gradle-dependencies/src/main/kotlin/com/huanshankeji/CommonVersions.kt`
- `buildSrc/src/main/kotlin/VersionsAndDependencies.kt`

**For Build Configuration:**
- Files ending in `-conventions.gradle.kts` contain reusable build logic
- `MavenPublishConventionsPlugin.kt` for publishing logic

## Trust These Instructions

This documentation is comprehensive and based on thorough exploration of the repository. Only perform additional searching if:
- Specific implementation details are needed beyond what's documented
- Instructions are found to be incorrect or incomplete
- New features or changes are being implemented that require understanding code not covered here

The build commands and project structure documented here are validated and current as of the repository state.