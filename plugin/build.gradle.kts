plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
}

pluginBundle {
    website = "https://github.com/huanshankeji/kotlin-common-gradle-plugin"
    vcsUrl = "https://github.com/huanshankeji/kotlin-common-gradle-plugin.git"
    tags = listOf("kotlin", "multiplatform")
}

group = "com.huanshankeji"
version = "0.1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        fun scriptConventionsPlugin(shortName: String, displayNameAndDescription: String) =
            getByName("com.huanshankeji.$shortName") {
                displayName = displayNameAndDescription
                description = displayNameAndDescription
            }

        scriptConventionsPlugin(
            "kotlin-jvm-common-conventions",
            "Kotlin/JVM common conventions"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-application-conventions",
            "Kotlin/JVM application conventions"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-library-conventions",
            "Kotlin/JVM library conventions"
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
    }
}
