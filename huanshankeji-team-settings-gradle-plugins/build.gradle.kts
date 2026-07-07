plugins {
    id("settings-plugin-conventions")
}

group = "com.huanshankeji.team"

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
