plugins {
    `aligned-version-plugin-conventions`
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.bundles.kotlinCommonGradlePlugins.implementation)
    api(libs.bundles.kotlinCommonGradlePlugins.api)

    testImplementation(kotlin("test"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "kotlin-multiplatform-js-browser-conventions",
            "Kotlin Multiplatform conventions with the JS browser target"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-conventional-targets",
            "Kotlin Multiplatform conventions with the conventional targets JVM, JS (browser), iOS (`iosX64`, `iosArm64`, and `iosSimulatorArm64`), and Wasm JS"
        )

        scriptConventionsPlugin(
            "maven-central-publish-conventions",
            "Maven Central publish conventions based on the `com.vanniktech.maven.publish` plugin."
        )
        scriptConventionsPlugin(
            "github-packages-maven-publish",
            "GitHub Packages publish",
            "Publishes to a Maven registry of GitHub Packages."
        )
        scriptConventionsPlugin(
            "gitlab-package-registry-project-level-maven-endpoint-publish",
            "GitLab Package Registry project-level Maven endpoint publish",
            "Publishes to a GitLab Package Registry project-level Maven endpoint."
        )

        scriptConventionsPlugin(
            "jvm-integration-test",
            "JVM integration test",
            "Adds a JVM integration test source set."
        )
        scriptConventionsPlugin(
            "jvm-test-common-feature-variant",
            "JVM test common feature variant",
            "Adds a JVM test common feature variant with a source set that depends on `main`."
        )

        run {
            scriptConventionsPlugin(
                "benchmark.kotlinx-benchmark-jvm-conventions",
                "kotlinx-benchmark conventions for Kotlin JVM",
                "Applies the kotlinx-benchmark and `allopen` plugins, adds the kotlinx-benchmark dependencies, " +
                        "and registers a separate `benchmarks` source set that depends on `main` by default."
            )
            scriptConventionsPlugin(
                "benchmark.kotlinx-benchmark-multiplatform-conventions",
                "kotlinx-benchmark conventions for Kotlin Multiplatform",
                "Applies the kotlinx-benchmark and `allopen` plugins and adds the koltinx-benchmark dependencies."
            )
        }

        scriptConventionsPlugin(
            "dokka.dokka-convention",
            "Dokka convention plugin"
        )
        scriptConventionsPlugin(
            "git-version",
            "Git commit-hash project version",
            "Sets the project version from Git: release branch uses the base version; otherwise `-dev-commit-<hash>` or `-dirty-SNAPSHOT`."
        )
        scriptConventionsPlugin(
            "kotlin-abi-validation-conventions",
            "Kotlin ABI validation conventions",
            "Enables Kotlin Gradle plugin ABI validation on JVM and Multiplatform projects."
        )
    }
}
