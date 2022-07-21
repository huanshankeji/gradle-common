plugins {
    id("plugin-conventions")
}

dependencies {
    //api(project(":common-gradle-dependencies"))
    //implementation("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.30.0")
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(shortName: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, shortName, displayName, description)

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

        scriptConventionsPlugin(
            "kotlin-jvm-library-default-maven-publish-conventions",
            "Kotlin/JVM library conventions with default Maven publish"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-jvm-and-js-browser-default-maven-publish-conventions",
            "Kotlin Multiplatform conventions with the JVM target and the JS browser target, and with default Maven publish"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-library-sonatype-ossrh-publish-conventions",
            "Kotlin/JVM library conventions with Sonatype OSSRH Maven Central publish"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-jvm-and-js-browser-sonatype-ossrh-publish-conventions",
            "Kotlin Multiplatform conventions with the JVM target and the JS browser target, and with Sonatype OSSRH Maven Central publish"
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
