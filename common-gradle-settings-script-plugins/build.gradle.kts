plugins {
    `aligned-version-plugin-conventions`
}

gradlePlugin {
    plugins {
        val `package` = group as String

        create("set-project-names-to-concatenated") {
            id = "$`package`.$name"
            implementationClass = "$`package`.SetProjectNamesToConcatenatedPlugin"
            displayName = "Set project names to concatenated"
            description = "Set every subproject's name to the form of its path concatenated with hyphens."
        }
    }
}
