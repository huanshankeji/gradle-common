import com.huanshankeji.projectVersionFromGitProvider

plugins {
    conventions
}

// TODO Should use the provider directly. It seems it's held back by the bootstrapping 'dokka-convention' plugin failing with this.
version = projectVersionFromGitProvider(
    commonGradleDependenciesBaseVersion, "common-gradle-dependencies-release",
).get()

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
