package com.huanshankeji

import org.gradle.api.tasks.Sync
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GenerateKotlinJsBrowserWebrootTest {
    @Test
    fun `registers the sync task immediately and reads the frontend path lazily`() {
        val root = ProjectBuilder.builder().withName("root").build()
        val frontend = ProjectBuilder.builder().withName("web").withParent(root).build()
        val backend = ProjectBuilder.builder().withName("server").withParent(root).build()

        frontend.tasks.register("jsBrowserDistribution", Sync::class.java)
        backend.pluginManager.apply("java")
        backend.pluginManager.apply("com.huanshankeji.generate-kotlin-js-browser-webroot-for-vertx-web")

        val syncTask = assertNotNull(
            backend.tasks.findByName("syncJsBrowserDistributionToResourcesWebroot")
        )

        val extension =
            backend.extensions.getByType(Generate_kotlin_js_browser_webroot_for_vertx_web_gradle.Extension::class.java)
        extension.webFrontendProjectPath.set(frontend.path)

        val sync = syncTask as Sync
        assertEquals(1, sync.source.files.size)
    }
}
