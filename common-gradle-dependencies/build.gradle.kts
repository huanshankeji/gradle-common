import com.huanshankeji.gitversioning.projectVersionFromGitProvider

plugins {
    conventions
}

// TODO don't use `afterEvaluate`
afterEvaluate {
    version = projectVersionFromGitProvider(commonGradleDependenciesBaseVersion).get()
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
