import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("kotlin-common-module-conventions")
    id("aligned-version-build-logic-conventions")
    id("project-gradle-plugins-conventions")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

kotlin {
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation()
}

dependencies {
    implementation(project(":kotlin-common-project-gradle-plugins"))
    implementation(libs.bundles.architectureCommonGradlePlugins.implementation)
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "default-web-frontend-conventions",
            "Default web frontend conventions for our projects with Compose for Web and kotlinx.html HTML generation"
        )
        scriptConventionsPlugin(
            "default-material-web-frontend-conventions",
            "(not implemented yet) Default web frontend conventions for our projects with Compose for Web, kotlinx.html HTML generation, and Material Design"
        )

        scriptConventionsPlugin(
            "generate-kotlin-js-browser-webroot-for-vertx-web",
            "Generate Kotlin/JS browser webroot for Vert.x Web",
            "Generate webroot from a Kotlin/JS subproject with browser target for Vert.x Web"
        )

        scriptConventionsPlugin(
            "jvm.native.osandarch.register-default-supported-feature-variants",
            "Register the OS and architecture feature variants",
            "Registers feature variants for different operating systems (Linux, Windows, macOS) and CPU architectures."
        )
    }
}
