plugins {
    `aligned-version-plugin-conventions`
}

repositories {
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(project(":kotlin-common-gradle-plugins"))
    //api(project(":common-gradle-dependencies"))
    //implementation(project(":common-gradle-dependencies"))
    /* This project depends on a specific version of the Maven dependency of "common-gradle-dependencies"
     since now they are developed together in the same branch `main`,
     enabling it to always depend on a release version. */

    implementation(commonGradleClasspathDependencies.composeMultiplatform.gradlePlugin.pluginProject())
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "kotlin-jvm-common-app-conventions",
            "Kotlin/JVM common app conventions"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-application-app-conventions",
            "Kotlin/JVM application app conventions"
        )
        scriptConventionsPlugin(
            "kotlin-jvm-library-app-conventions",
            "Kotlin/JVM library app conventions"
        )

        scriptConventionsPlugin(
            "kotlin-multiplatform-app-conventions",
            "Kotlin Multiplatform app conventions"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-js-browser-app-conventions",
            "Kotlin Multiplatform app conventions with the JS browser target"
        )
        scriptConventionsPlugin(
            "kotlin-multiplatform-jvm-and-js-browser-app-conventions",
            "Kotlin Multiplatform app conventions with the JVM target and the JS browser target"
        )

        scriptConventionsPlugin(
            "default-web-frontend-conventions",
            "Default web frontend conventions for our projects with Compose for Web and kotlinx.html HTML generation"
        )
        // TODO
        scriptConventionsPlugin(
            "default-material-web-frontend-conventions",
            "(not implemented yet) Default web frontend conventions for our projects with Compose for Web, kotlinx.html HTML generation, and Material Design"
        )

        run {
            val name = "generate-kotlin-js-browser-webroot-for-vertx-web"
            create(name) {
                id = "$`package`.$name"
                implementationClass = "$`package`.GenerateKotlinJsBrowserWebrootForVertxWebPlugin"
                displayName = "Generate Kotlin/JS browser webroot for Vert.x Web"
                description = "Generate webroot from a Kotlin/JS subproject with browser target for Vert.x Web"
            }
        }

        scriptConventionsPlugin(
            "jvm.native.osandarch.register-default-supported-feature-variants",
            "Register the OS and architecture feature variants",
            "Registers feature variants for different operating systems (Linux, Windows, macOS) and CPU architectures."
        )
    }
}
