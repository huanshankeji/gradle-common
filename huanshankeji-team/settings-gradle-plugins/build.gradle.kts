plugins {
    `settings-gradle-plugins-conventions`
    `huanshankeji-team-module`
}

dependencies {
    api(project(":huanshankeji-team:gradle-library"))
    api(project(":kotlin-common:kotlin-common-settings-gradle-plugins"))
}

gradlePlugin {
    plugins {
        val `package` = group as String
        fun scriptPlugin(
            idSuffix: String,
            displayName: String,
            description: String = displayName,
        ) =
            commonScriptPlugin(`package`, idSuffix, displayName, description)
    }

    // currently emtpy but reserved for future use
}
