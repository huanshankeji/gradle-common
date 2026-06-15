import com.huanshankeji.SourceFile
import com.huanshankeji.generateKotlinSources
import com.huanshankeji.projectVersionFromGitProvider
import kotlin.reflect.full.memberProperties

plugins {
    conventions
}

// TODO Should use the provider direcctly. It seems it's hold back by the bootstraping 'dokka-convention' plugin failing with this.
version = projectVersionFromGitProvider(
    commonGradleDependenciesBaseVersion, "common-gradle-dependencies-release",
).get()

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
