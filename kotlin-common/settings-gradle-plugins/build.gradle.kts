plugins {
    id("kotlin-common-module-conventions")
    id("aligned-version-build-logic-conventions")
    id("settings-gradle-plugins-conventions")
}

dependencies {
    implementation("org.gradle.toolchains.foojay-resolver-convention:org.gradle.toolchains.foojay-resolver-convention.gradle.plugin:1.0.0")
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "base-settings-conventions",
            "Base settings conventions",
            "Applies common settings plugins such as the Foojay toolchain resolver convention.",
        )
    }
}
