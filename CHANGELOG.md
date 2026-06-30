# Change log

## Unreleased

* reorganize plugin helper packages and modules
    * move universal Git versioning, GitHub Packages, and GitLab Package Registry helpers into `com.huanshankeji.gitversion`, `com.huanshankeji.github.packages`, and `com.huanshankeji.gitlab.packageregistry` in `kotlin-common-gradle-plugins`
    * move Huanshankeji-specific Maven repository content filtering to `huanshankeji-team-gradle-plugins`
    * rename plugin ids: `com.huanshankeji.gitversion.git-version`, `com.huanshankeji.github.packages.github-packages-maven-publish`, and `com.huanshankeji.gitlab.packageregistry.gitlab-package-registry-project-level-maven-endpoint-publish`
    * register `com.huanshankeji.team.public-open-source-dependency-repositories` settings plugin
* remove duplicated build logic now that `buildSrc` source-links the plugin module sources (#54, #60)
    * drop the inlined `GitVersion.kt`, `kotlin-abi-validation`, and `dokka-convention` copies from `buildSrc`
    * dogfood `com.huanshankeji.gitversion.git-version`, `com.huanshankeji.kotlin-abi-validation-conventions`, and `com.huanshankeji.team.dokka.github-dokka-convention` instead
* remove the cross-version bootstrapping self-dependencies (#54)
    * `buildSrc` is now a multi-project build whose subprojects source-link the corresponding root modules' sources (`common-gradle-dependencies`, `kotlin-common-gradle-plugins`, `huanshankeji-team-gradle-plugins`) instead of depending on a stale released `com.huanshankeji.team:gradle-plugins`
    * the plugin modules now depend on the `common-gradle-dependencies` project directly instead of a stale released version
    * dependency versions/coordinates used by the build scripts are centralized in a single shared version catalog `gradle/libs.versions.toml`, shared by both the root build and `buildSrc`
    * stop generating `GeneratedVersions` from `buildSrc`'s `DependencyVersions`; the versions are now declared directly in `CommonVersions` and kept in sync by hand with the shared version catalog (to be unified by #9)
* update Gradle to 9.6.0
* bump Kotlin to 2.4.0, including `CommonVersions` and `gradle-kotlin-dsl-plugins` 6.7.3 for build logic

## Outdated historical per-artifact change logs

Releases before the merged change log are documented separately by artifact:

* [Gradle plugins](PLUGINS_CHANGELOG.md) (deprecated)
* [common-gradle-dependencies](COMMON_GRADLE_DEPENDENCIES_CHANGELOG.md) (deprecated)
