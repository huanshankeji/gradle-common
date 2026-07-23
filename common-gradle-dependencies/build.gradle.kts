import com.huanshankeji.gitversioning.devCommitOrReleaseVersionProvider

plugins {
    `common-conventions`
}

version = providers.devCommitOrReleaseVersionProvider(commonGradleDependenciesBaseVersion, isRelease).get()

gradlePlugin {
    plugins {
        commonScriptPlugin(
            group as String,
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing this module to the Gradle Plugin Portal"
        )
    }
}
