plugins {
    `settings-gradle-plugins-conventions`
    `kotlin-common-module`
}

dependencies {
    implementation(libs.gradle.foojayResolverConvention)
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            commonScriptPlugin(`package`, idSuffix, displayName, description)

        scriptPlugin(
            "base-settings-conventions",
            "Base settings conventions",
            "Applies common settings plugins such as the Foojay toolchain resolver convention.",
        )
    }
}
