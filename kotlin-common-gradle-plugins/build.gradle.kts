plugins {
    `aligned-version-plugin-conventions`
}

dependencies {
    implementation(libs.bundles.kotlinCommonGradlePluginsImplementation)
    // made `api` to expose the plugin extension class (https://github.com/vanniktech/gradle-maven-publish-plugin/blob/main/plugin/src/main/kotlin/com/vanniktech/maven/publish/MavenPublishBaseExtension.kt)
    api(libs.vanniktech.mavenPublish.gradlePlugin)

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
            "gitlab-project-level-maven-endpoint-publish",
            "GitLab project-level Maven endpoint publish",
            "Publishes to a GitLab project-level Maven endpoint."
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
    }
}
