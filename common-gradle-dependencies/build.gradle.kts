import com.huanshankeji.projectVersionFromGitProvider

plugins {
    conventions
}

version = projectVersionFromGitProvider(commonGradleDependenciesBaseVersion)

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
