plugins {
    `aligned-version-plugin-conventions`
}

dependencies {
    //implementation("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.30.0")

    testImplementation(kotlin("test"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "kotlin-jvm-common-conventions",
            "Kotlin/JVM common conventions"
        )

        scriptConventionsPlugin(
            "kotlin-multiplatform-conventions",
            "Kotlin Multiplatform conventions"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-js-browser-conventions",
            "Kotlin Multiplatform conventions with the JS browser target"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-jvm-and-js-browser-conventions",
            "Kotlin Multiplatform conventions with the JVM target and the JS browser target"
        )

        create("maven-publish-conventions") {
            id = "$`package`.$name"
            implementationClass = "$`package`.MavenPublishConventionsPlugin"
            displayName = "Maven publish conventions"
            description = displayName
        }
        scriptConventionsPlugin(
            "java-1-8-compatibility-publish-conventions",
            "Java conventions with Javadoc, sources, and 1.8 compatibility for publish"
        )
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

        // TODO
        scriptConventionsPlugin(
            "nexus-staging",
            "Not implemented yet"
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
    }
}
