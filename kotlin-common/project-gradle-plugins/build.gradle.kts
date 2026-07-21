plugins {
    `project-gradle-plugins-conventions`
    `kotlin-common-module`
}

dependencies {
    api(project(":kotlin-common:kotlin-common-gradle-library"))
    implementation(libs.bundles.kotlinCommonProjectGradlePlugins.implementation)
    api(libs.bundles.kotlinCommonProjectGradlePlugins.api)

    testImplementation(kotlin("test"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            commonScriptPlugin(`package`, idSuffix, displayName, description)

        scriptPlugin(
            "kotlin-multiplatform-js-browser-conventions",
            "Kotlin Multiplatform conventions with the JS browser target"
        )
        scriptPlugin(
            "kotlin-multiplatform-conventional-targets",
            "Kotlin Multiplatform conventions with the conventional targets JVM, JS (browser), iOS (`iosX64`, `iosArm64`, and `iosSimulatorArm64`), and Wasm JS"
        )

        scriptPlugin(
            "publish.maven.central.conventions",
            "Maven Central publish conventions based on the `com.vanniktech.maven.publish` plugin. " +
                    "Always configures signing for all publications (`-SNAPSHOT` versions are not required to sign). " +
                    "For custom signing rules, apply `com.vanniktech.maven.publish` yourself and configure signing."
        )
        scriptPlugin(
            "maven-central-publish-conventions",
            "Maven Central publish conventions based on the `com.vanniktech.maven.publish` plugin. " +
                    "(deprecated, replaced by `com.huanshankeji.publish.maven.central.conventions`)"
        )
        scriptPlugin(
            "gitversioning.opensourceconvention.githubpackages.publish",
            "Open-source convention GitHub Packages + Maven Central publish",
            "Publishes non-release / `*-dev-commit-*` versions to GitHub Packages and release versions to Maven Central. " +
                    "Call required `openSourceConventionGithubPackagesPublish.signAllPublicationsIfRelease(isRelease)` " +
                    "(enables signing on release and gates publish tasks by destination). " +
                    "Team defaults: `com.huanshankeji.team.gitversioning.opensourceconvention.githubpackages.publish`."
        )
        scriptPlugin(
            "github.packages.maven.publish",
            "GitHub Packages publish",
            "Publishes to a Maven registry of GitHub Packages."
        )
        scriptPlugin(
            "github-packages-maven-publish",
            "GitHub Packages publish (deprecated, replaced by `com.huanshankeji.github.packages.maven.publish`)",
        )

        scriptPlugin(
            "gitlab.packageregistry.maven.project-level-endpoint-publish",
            "GitLab Package Registry project-level Maven endpoint publish",
            "Publishes to a GitLab Package Registry project-level Maven endpoint."
        )
        scriptPlugin(
            "gitlab-project-level-maven-endpoint-publish",
            "GitLab project-level Maven endpoint publish (deprecated, replaced by `com.huanshankeji.gitlab.packageregistry.maven.project-level-endpoint-publish`)",
        )

        scriptPlugin(
            "jvm-integration-test",
            "JVM integration test",
            "Adds a JVM integration test source set."
        )
        scriptPlugin(
            "jvm-test-common-feature-variant",
            "JVM test common feature variant",
            "Adds a JVM test common feature variant with a source set that depends on `main`."
        )

        run {
            scriptPlugin(
                "benchmark.kotlinx-benchmark-jvm-conventions",
                "kotlinx-benchmark conventions for Kotlin JVM",
                "Applies the kotlinx-benchmark and `allopen` plugins, adds the kotlinx-benchmark dependencies, " +
                        "and registers a separate `benchmarks` source set that depends on `main` by default."
            )
            scriptPlugin(
                "benchmark.kotlinx-benchmark-multiplatform-conventions",
                "kotlinx-benchmark conventions for Kotlin Multiplatform",
                "Applies the kotlinx-benchmark and `allopen` plugins and adds the koltinx-benchmark dependencies."
            )
        }

        scriptPlugin(
            "dokka.dokka-convention",
            "Dokka convention plugin"
        )
        scriptPlugin(
            "root-project-conventions",
            "Root project conventions",
            "Applies root project plugins such as the Gradle versions plugin."
        )
    }
}
