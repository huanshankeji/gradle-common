import com.huanshankeji.SourceFile
import com.huanshankeji.generateKotlinSources
import com.huanshankeji.gitversioning.projectVersionFromGitProvider

plugins {
    `common-conventions`
}

version = projectVersionFromGitProvider(commonGradleDependenciesBaseVersion).get()

// Bake overlapping catalog versions into sources so `CommonVersions` defaults stay in sync
// with `gradle/libs.versions.toml` without hand-duplicating the strings (#9 partial).
generateKotlinSources(
    sourceFiles = listOf(
        SourceFile(
            "GeneratedVersions.kt",
            """
package com.huanshankeji

internal object GeneratedVersions {
    internal const val kotlin = "${libs.versions.kotlin.get()}"
    internal const val composeMultiplatform = "${libs.versions.composeMultiplatform.get()}"
    internal const val kotlinxBenchmark = "${libs.versions.kotlinx.benchmark.asProvider().get()}"
}
""".trimIndent() + "\n"
        )
    )
)

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "common-gradle-dependencies-dummy-plugin",
            "A dummy plugin defined to enable publishing the package to the Gradle Plugin Portal"
        )
    }
}
