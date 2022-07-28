plugins {
    id("build-dependency-library-conventions")
}

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
version = "0.3.0-20220727"

// copied from GenerateKotlinSources.kt in "kotlin-common-gradle-plugins"
// Depending on a version of "kotlin-common-gradle-plugins" directly might lead to a dependency hell.
class SourceFile(val filePath: String, val content: String)

fun Project.generateKotlinSources(
    taskName: String = "generateSources", sourceDirectoryName: String = "main", sourceFiles: List<SourceFile>
) {
    val generatedSourcesDir = buildDir.resolve("gen/$sourceDirectoryName/kotlin")

    val task = tasks.register(taskName) {
        generatedSourcesDir.mkdirs()

        for (sourceFile in sourceFiles) {
            val generatedVersionsSourceFile = generatedSourcesDir.resolve(sourceFile.filePath)
            generatedVersionsSourceFile.writeText(sourceFile.content)
        }
    }

    tasks.compileKotlin {
        dependsOn(task)
    }

    kotlin.sourceSets["main"].kotlin.srcDir(generatedSourcesDir)
}

generateKotlinSources(
    sourceFiles = listOf(
        SourceFile(
            "GeneratedKotlinVersion.kt",
            "internal const val kotlinVersion = \"$kotlinVersion\"\n"
        )
    )
)
