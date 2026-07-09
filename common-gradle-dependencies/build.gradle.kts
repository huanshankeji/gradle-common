import com.huanshankeji.gitversioning.projectVersionFromGitProvider

plugins {
    `common-conventions`
}

version = projectVersionFromGitProvider(commonGradleDependenciesBaseVersion).get()

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
