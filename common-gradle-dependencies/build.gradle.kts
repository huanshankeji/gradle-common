import com.huanshankeji.SourceFile
import com.huanshankeji.generateKotlinSources

plugins {
    conventions
}

version = commonGradleDependenciesVersion

generateKotlinSources(
    sourceFiles = listOf(
        SourceFile(
            "GeneratedKotlinVersion.kt",
            "internal const val kotlinVersion = \"$kotlinVersion\"\n" +
                    "internal const val composeMultiplatformVersion = \"$kotlinVersion\"\n"
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
