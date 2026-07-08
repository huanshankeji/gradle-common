plugins {
    id("huanshankeji-team-module-conventions")
    id("aligned-version-build-logic-conventions")
    id("settings-gradle-plugins-conventions")
}

dependencies {
    implementation(project(":huanshankeji-team:gradle-library"))
}

gradlePlugin {
    plugins {
        fun scriptConventionsPlugin(
            `package`: String,
            idSuffix: String,
            displayName: String,
            description: String = displayName,
        ) = scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin(
            "com.huanshankeji.team.gitversioning",
            "public-open-source-dependency-repositories",
            "Public open-source dependency repositories",
            "Composable settings DSL for Huanshankeji public OSS Maven repositories.",
        )
    }
}
