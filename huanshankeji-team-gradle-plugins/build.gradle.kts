plugins {
    id("aligned-version-plugin-conventions")
}

group = "com.huanshankeji.team"

dependencies {
    // `api` is needed to set `com.huanshankeji.GithubPackagesPublishPlugin.Extension.repository`
    api(project(":kotlin-common-gradle-plugins"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptConventionsPlugin(shortName: String, displayName: String, description: String = displayName) =
            scriptPlugin(`package`, shortName, displayName, description)

        scriptConventionsPlugin("with-group", "With Huanshankeji team's group, aka \"com.huanshankeji\"")
        scriptConventionsPlugin(
            "github-packages-maven-publish",
            "GitHub Packages Maven publish (to Huanshankeji team's repository)"
        )
        scriptConventionsPlugin(
            "default-github-packages-maven-publish",
            "GitHub Packages Maven publish (to Huanshankeji team's repository) with default conventions"
        )
    }
}
