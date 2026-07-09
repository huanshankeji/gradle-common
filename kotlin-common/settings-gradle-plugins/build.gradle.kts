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
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "base-settings-conventions",
            "Base settings conventions",
            "Applies common settings plugins such as the Foojay toolchain resolver convention.",
        )
    }
}
