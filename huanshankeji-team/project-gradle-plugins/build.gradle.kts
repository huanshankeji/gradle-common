plugins {
    `project-gradle-plugins-conventions`
    `huanshankeji-team-module`
}

dependencies {
    implementation(project(":huanshankeji-team:gradle-library"))
    api(project(":kotlin-common-project-gradle-plugins"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(idSuffix: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, idSuffix, displayName, description)

        scriptConventionsPlugin("with-group", "With Huanshankeji team's group, aka \"com.huanshankeji\"")
        scriptConventionsPlugin(
            "github.packages.maven.publish",
            "GitHub Packages Maven publish (to Huanshankeji team's repository)"
        )

        scriptConventionsPlugin(
            "dokka.github-dokka-convention",
            "Dokka convention plugin for projects on GitHub",
            "Please apply this plugin after setting the project version."
        )
    }
}
