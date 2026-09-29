package com.huanshankeji

import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText
import kotlin.test.Test

class AfterEvaluateReplacementFunctionalTest {
    @Test
    fun `jvm benchmark conventions honor sourceSetType set in the build script`() {
        val projectDir = fixtureDir()
        projectDir.resolve("settings.gradle.kts").writeText(
            """
            rootProject.name = "benchmark-conventions"
            include("jvm-default", "jvm-main")
            """.trimIndent()
        )
        projectDir.resolve("build.gradle.kts").writeText(
            """
            subprojects {
                repositories { mavenCentral() }
            }
            """.trimIndent()
        )

        val jvmDefault = projectDir.resolve("jvm-default")
        Files.createDirectories(jvmDefault)
        jvmDefault.resolve("build.gradle.kts").writeText(
            """
            plugins {
                id("com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions")
            }

            tasks.register("assertConventions") {
                doLast {
                    val sourceSetNames = project.extensions.getByType(org.gradle.api.tasks.SourceSetContainer::class.java).names
                    check("benchmarks" in sourceSetNames) { "source sets: ${'$'}sourceSetNames" }
                    val runtime = project.configurations.getByName("benchmarksImplementation").dependencies
                    check(runtime.any { it.name == "kotlinx-benchmark-runtime" }) { runtime.map { it.name } }
                    val targets = project.extensions.getByType(kotlinx.benchmark.gradle.BenchmarksExtension::class.java).targets.names
                    check("benchmarks" in targets) { "targets: ${'$'}targets" }
                }
            }
            """.trimIndent()
        )

        val jvmMain = projectDir.resolve("jvm-main")
        Files.createDirectories(jvmMain)
        jvmMain.resolve("build.gradle.kts").writeText(
            """
            import com.huanshankeji.SourceSetType

            plugins {
                id("com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions")
            }

            kotlinxBenchmarkConventions {
                sourceSetType.set(SourceSetType.Main)
            }

            tasks.register("assertConventions") {
                doLast {
                    val sourceSetNames = project.extensions.getByType(org.gradle.api.tasks.SourceSetContainer::class.java).names
                    check("benchmarks" !in sourceSetNames) { "source sets: ${'$'}sourceSetNames" }
                    val runtime = project.configurations.getByName("implementation").dependencies
                    check(runtime.any { it.name == "kotlinx-benchmark-runtime" }) { runtime.map { it.name } }
                    val targets = project.extensions.getByType(kotlinx.benchmark.gradle.BenchmarksExtension::class.java).targets.names
                    check("main" in targets) { "targets: ${'$'}targets" }
                }
            }
            """.trimIndent()
        )

        GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments(":jvm-default:assertConventions", ":jvm-main:assertConventions", "--stacktrace")
            .forwardOutput()
            .build()
    }

    @Test
    fun `multiplatform benchmark conventions register targets added in the build script`() {
        val projectDir = fixtureDir()
        projectDir.resolve("settings.gradle.kts").writeText(
            """
            rootProject.name = "mpp-benchmark"
            """.trimIndent()
        )
        projectDir.resolve("build.gradle.kts").writeText(
            """
            plugins {
                id("com.huanshankeji.benchmark.kotlinx-benchmark-multiplatform-conventions")
            }

            kotlin {
                jvm()
            }

            repositories { mavenCentral() }

            tasks.register("assertConventions") {
                doLast {
                    val targets = project.extensions.getByType(kotlinx.benchmark.gradle.BenchmarksExtension::class.java).targets.names
                    check("jvm" in targets) { "targets: ${'$'}targets" }
                }
            }
            """.trimIndent()
        )

        GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments("assertConventions", "--stacktrace")
            .forwardOutput()
            .build()
    }

    @Test
    fun `deprecated package publish plugins wire repositories from extension properties`() {
        val projectDir = fixtureDir()
        projectDir.resolve("settings.gradle.kts").writeText(
            """
            rootProject.name = "package-publish"
            """.trimIndent()
        )
        projectDir.resolve("build.gradle.kts").writeText(
            """
            plugins {
                id("com.huanshankeji.github-packages-maven-publish")
                id("com.huanshankeji.gitlab-project-level-maven-endpoint-publish")
            }

            group = "com.example"
            version = "0.0.1"

            githubPackagesPublish {
                owner.set("huanshankeji")
                repository.set("gradle-common")
            }

            gitlabPackagesPublish {
                projectId.set("123")
            }

            tasks.register("assertRepositories") {
                doLast {
                    val publishing = project.extensions.getByType(org.gradle.api.publish.PublishingExtension::class.java)
                    val github = publishing.repositories.getByName("GitHubPackages") as org.gradle.api.artifacts.repositories.MavenArtifactRepository
                    check(github.url.toString() == "https://maven.pkg.github.com/huanshankeji/gradle-common") { github.url }
                    val gitlab = publishing.repositories.getByName("GitLab") as org.gradle.api.artifacts.repositories.MavenArtifactRepository
                    check(gitlab.url.toString() == "https://gitlab.com/api/v4/projects/123/packages/maven") { gitlab.url }
                }
            }
            """.trimIndent()
        )

        GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments("assertRepositories", "--stacktrace")
            .forwardOutput()
            .build()
    }
}

private fun fixtureDir(): Path =
    Files.createTempDirectory("after-evaluate-replacement")
