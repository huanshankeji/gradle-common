package com.huanshankeji

import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertEquals

class GenerateKotlinJsBrowserWebrootFunctionalTest {
    @Test
    fun `sync task reads the frontend path set after the plugin is applied`() {
        val projectDir = Files.createTempDirectory("js-webroot")
        projectDir.resolve("settings.gradle.kts").writeText(
            """
            rootProject.name = "js-webroot"
            include("frontend", "backend")
            """.trimIndent()
        )
        val frontend = projectDir.resolve("frontend")
        Files.createDirectories(frontend.resolve("static"))
        frontend.resolve("static/hello.txt").writeText("hello")
        frontend.resolve("build.gradle.kts").writeText(
            """
            plugins { base }

            tasks.register<Sync>("jsBrowserDistribution") {
                from("static")
                into(layout.buildDirectory.dir("dist"))
            }
            """.trimIndent()
        )
        val backend = projectDir.resolve("backend")
        Files.createDirectories(backend)
        backend.resolve("build.gradle.kts").writeText(
            """
            plugins {
                java
                id("com.huanshankeji.generate-kotlin-js-browser-webroot-for-vertx-web")
            }

            generateKotlinJsResources {
                webFrontendProjectPath.set(":frontend")
            }
            """.trimIndent()
        )

        GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments(":backend:processResources", "--stacktrace")
            .forwardOutput()
            .build()

        assertEquals(
            "hello",
            backend.resolve("build/resources/main/webroot/hello.txt").readText(),
        )
    }
}
