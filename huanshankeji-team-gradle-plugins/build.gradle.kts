plugins {
    `aligned-version-plugin-conventions`
}

group = "com.huanshankeji.team"

dependencies {
    // `api` is needed for the team `github-packages-maven-publish` plugin to configure the base plugin's extension
    api(project(":kotlin-common-gradle-plugins"))
    implementation(libs.benManes.gradleVersionsPlugin)
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin("with-group", "With Huanshankeji team's group, aka \"com.huanshankeji\"")
        scriptConventionsPlugin(
            "github-packages-maven-publish",
            "GitHub Packages Maven publish (to Huanshankeji team's repository)"
        )
        scriptConventionsPlugin(
            "default-github-packages-maven-publish",
            "GitHub Packages Maven publish (to Huanshankeji team's repository) with default conventions"
        )

        scriptConventionsPlugin(
            "dokka.github-dokka-convention",
            "Dokka convention plugin for projects on GitHub",
            "Please apply this plugin after setting the project version."
        )
        scriptConventionsPlugin(
            "root-project-conventions",
            "Root project conventions",
            "Applies team-wide root project plugins such as the Gradle versions plugin."
        )
        scriptConventionsPlugin(
            "public-open-source-dependency-repositories",
            "Public open-source dependency repositories",
            "Composable settings DSL for Maven repositories used by public Huanshankeji OSS repos."
        )
    }
}
