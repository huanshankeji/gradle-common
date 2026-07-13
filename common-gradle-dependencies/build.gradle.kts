import com.huanshankeji.gitversioning.devCommitVersionProvider

plugins {
    `common-conventions`
}

version = providers.devCommitVersionProvider(commonGradleDependenciesBaseVersion).get()

gradlePlugin {
    plugins {
       commonScriptPlugin(
            group as String,
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
