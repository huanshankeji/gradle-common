plugins {
    `project-gradle-plugins-conventions`
}

dependencies {
    implementation(project(":kotlin-common:kotlin-common-project-gradle-plugins"))
    implementation(libs.bundles.architectureCommonGradlePlugins.implementation)

    //api(project(":common-gradle-dependencies"))
    //implementation(project(":common-gradle-dependencies"))
    /* This project depends on a specific version of the Maven dependency of "common-gradle-dependencies"
     since now they are developed together in the same branch `main`,
     enabling it to always depend on a release version. */

    // implementation(commonGradleClasspathDependencies.composeMultiplatform.gradlePlugin.pluginProject()) // bootstrapping
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            commonScriptPlugin(`package`, idSuffix, displayName, description)

        scriptPlugin(
            "web-frontend-conventions",
            "Web frontend conventions for our projects with Compose for Web and kotlinx.html HTML generation"
        )
        scriptPlugin(
            "default-web-frontend-conventions",
            "Default web frontend conventions for our projects with Compose for Web and kotlinx.html HTML generation " +
                    "(deprecated, replaced by `com.huanshankeji.web-frontend-conventions`)"
        )
        // TODO
        scriptPlugin(
            "material-web-frontend-conventions",
            "(not implemented yet) Material web frontend conventions for our projects with Compose for Web, kotlinx.html HTML generation, and Material Design"
        )
        scriptPlugin(
            "default-material-web-frontend-conventions",
            "(not implemented yet) Default web frontend conventions for our projects with Compose for Web, kotlinx.html HTML generation, and Material Design " +
                    "(deprecated, replaced by `com.huanshankeji.material-web-frontend-conventions`)"
        )

        scriptPlugin(
            "generate-kotlin-js-browser-webroot-for-vertx-web",
            "Generate Kotlin/JS browser webroot for Vert.x Web",
            "Generate webroot from a Kotlin/JS subproject with browser target for Vert.x Web"
        )

        scriptPlugin(
            "jvm.native.osandarch.register-default-supported-feature-variants",
            "Register the OS and architecture feature variants",
            "Registers feature variants for different operating systems (Linux, Windows, macOS) and CPU architectures."
        )
    }
}
