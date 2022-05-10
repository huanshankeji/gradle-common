plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.1.1")
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
        val commonPackage = group
        fun scriptConventionsPlugin(shortName: String, displayNameAndDescription: String) =
            getByName("$commonPackage.$shortName") {
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

        scriptConventionsPlugin(
            "default-web-frontend-conventions",
            "Default web frontend conventions for our projects with Compose for Web and kotlinx.html HTML generation"
        )
        /* TODO
        scriptConventionsPlugin(
            "default-material-web-frontend-conventions",
            "Default web frontend conventions for our projects with Compose for Web, kotlinx.html HTML generation, and Material Design"
        )
        */

        val name = "generate-kotlin-js-browser-webroot-for-vertx-web"
        create(name) {
            id = "$commonPackage.$name"
            implementationClass = "$commonPackage.GenerateKotlinJsBrowserWebrootForVertxWebPlugin"
            displayName = "Generate Kotlin/JS browser webroot for Vert.x Web"
            description = "Generate webroot from a Kotlin/JS with browser target for Vert.x Web"
        }
    }
}
