import com.huanshankeji.SourceFile
import com.huanshankeji.generateKotlinSources
import kotlin.reflect.full.memberProperties

plugins {
    conventions
}

version = commonGradleDependenciesVersion

generateKotlinSources(
    sourceFiles = listOf(
        SourceFile(
            "GeneratedKotlinVersion.kt",
            """
internal object GeneratedVersions {
${
                DependencyVersions::class.memberProperties.joinToString("\n") {
                    "    internal const val ${it.name} = \"${it(DependencyVersions)}\""
                }
            }
}
""".drop(1)
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
