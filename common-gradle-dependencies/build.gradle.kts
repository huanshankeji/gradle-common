plugins {
    `build-dependency-library-conventions`
    `java-gradle-plugin` // This plugin is needed to add the necessary Gradle dependencies.
    signing
}

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
version = "0.3.1-20220728-SNAPSHOT"

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

fun Project.generateKotlinVersion(kotlinVersion: String) =
    generateKotlinSources(
        sourceFiles = listOf(
            SourceFile(
                "GeneratedKotlinVersion.kt",
                "internal const val kotlinVersion = \"$kotlinVersion\"\n"
            )
        )
    )

generateKotlinVersion(kotlinVersion)


// copied from "kotlin-jvm-library-maven-publish-conventions.gradle.kts"
signing {
    isRequired = false
    sign(publishing.publications)
}

// copied from "sonatype-ossrh-publish.gradle.kts"
publishing {
    publishing.repositories.maven {
        name = "SonatypeOssrh"
        val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        credentials {
            project.findProperty("ossrhUsername")?.let { username = it as String }
            project.findProperty("ossrhPassword")?.let { password = it as String }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }

        withType<MavenPublication> {
            pom {
                /*
                name.set("Huanshankeji Gradle Common (in and for Kotlin)")
                description.set("Huanshankeji's Gradle common code in Kotlin, mainly for common projects in Kotlin")
                */
                name.set("Huanshankeji common Gradle dependencies")
                description.set("Huanshankeji's common Gradle dependencies in Kotlin, mainly for common projects in Kotlin")

                url.set("$GITHUB_URL/${project.name}")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ShreckYe")
                        name.set("Shreck Ye")
                        email.set("ShreckYe@gmail.com")
                    }
                }
                scm {
                    connection.set(GITHUB_GIT_URL)
                    developerConnection.set(GITHUB_GIT_URL)
                    url.set(GITHUB_URL)
                }
            }
        }
    }
}
