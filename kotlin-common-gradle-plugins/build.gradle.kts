plugins {
    id("plugin-conventions")
}

dependencies {
    //api(project(":common-gradle-dependencies"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(shortName: String, displayNameAndDescription: String) =
            scriptPlugin(`package`, shortName, displayNameAndDescription)

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
    }
}
