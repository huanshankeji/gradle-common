plugins {
    `aligned-version-plugin-conventions`
}

dependencies {
    //implementation("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.30.0")

    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-plugin:${DependencyVersions.kotlinxBenchmark}")
    implementation(kotlin("allopen", DependencyVersions.kotlin))
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${DependencyVersions.dokka}")

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

        create("maven-publish-conventions") {
            id = "$`package`.$name"
            implementationClass = "$`package`.MavenPublishConventionsPlugin"
            displayName = "Maven publish conventions"
            description = displayName
        }
        scriptConventionsPlugin(
            "kotlin-jvm-library-maven-publish-conventions",
            "Kotlin/JVM library conventions with Maven publish"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-maven-publish-conventions",
            "Kotlin Multiplatform conventions with Maven publish"
        )
        scriptConventionsPlugin(
            "sonatype-ossrh-publish",
            "Sonatype OSSRH Maven Central publish"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-library-sonatype-ossrh-publish-conventions",
            "Kotlin/JVM library conventions with Sonatype OSSRH Maven Central publish"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-sonatype-ossrh-publish-conventions",
            "Kotlin Multiplatform conventions with Sonatype OSSRH Maven Central publish"
        )
        create("github-packages-maven-publish") {
            id = "$`package`.$name"
            implementationClass = "$`package`.GithubPackagesMavenPublishPlugin"
            displayName = "GitHub Packages publish"
            description = "Publishes to a Maven registry of GitHub Packages."
        }
        create("gitlab-project-level-maven-endpoint-publish") {
            id = "$`package`.$name"
            implementationClass = "$`package`.GitlabProjectLevelMavenEndpointPublishPlugin"
            displayName = "GitLab project-level Maven endpoint publish"
            description = "Publishes to a GitLab project-level Maven endpoint."
        }

        /*
        // TODO
        scriptConventionsPlugin(
            "nexus-staging",
            "Not implemented yet"
        )
        */

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
