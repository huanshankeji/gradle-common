plugins {
    conventions
    id("com.huanshankeji.git-version")
}

gitVersion {
    baseVersion.set(commonGradleDependenciesBaseVersion)
}

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
