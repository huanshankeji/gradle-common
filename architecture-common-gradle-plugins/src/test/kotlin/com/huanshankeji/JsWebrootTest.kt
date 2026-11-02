package com.huanshankeji

import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JsWebrootTest {
    @Test
    fun `webroot tracks late configuration and builds a later frontend project`() = checkWebroot(true)

    @Test
    fun `default webroot includes all distribution files`() = checkWebroot(false)

    private fun checkWebroot(custom: Boolean) {
        val directory = Files.createTempDirectory("js-webroot").toFile()
        try {
            directory.resolve("settings.gradle").writeText(
                "rootProject.name = 'webroot-test'\ninclude 'backend', 'frontend'"
            )
            directory.resolve("backend").mkdirs()
            directory.resolve("backend/build.gradle").writeText(
                """
                plugins {
                    id 'java'
                    id 'com.huanshankeji.generate-kotlin-js-browser-webroot-for-vertx-web'
                }
                tasks.named('syncJsBrowserDistributionToResourcesWebroot').get()
                generateKotlinJsResources {
                    webFrontendProjectPath.set(':frontend')
                    ${if (custom) "includes.set(['**/*.js']); webRoot.set('assets')" else ""}
                }
                layout.buildDirectory.set(layout.projectDirectory.dir('custom-build'))
                """.trimIndent()
            )
            directory.resolve("frontend/input").mkdirs()
            directory.resolve("frontend/input/app.js").writeText("console.log('test');")
            directory.resolve("frontend/input/ignored.txt").writeText("excluded")
            directory.resolve("frontend/build.gradle").writeText(
                """
                tasks.register('jsBrowserDistribution', Sync) {
                    from('input')
                    into(layout.buildDirectory.dir('distribution'))
                }
                """.trimIndent()
            )
            val runner = GradleRunner.create().withProjectDir(directory).withPluginClasspath()
                .withArguments(":backend:processResources", "--configuration-cache", "--stacktrace", "--offline")
            runner.build()
            val resources = directory.resolve("backend/custom-build/resources/main/${if (custom) "assets" else "webroot"}")
            assertEquals("console.log('test');", resources.resolve("app.js").readText())
            if (custom)
                assertFalse(resources.resolve("ignored.txt").exists())
            else
                assertEquals("excluded", resources.resolve("ignored.txt").readText())
            assertTrue(runner.build().output.contains("Reusing configuration cache."))
        } finally {
            directory.deleteRecursively()
        }
    }
}
