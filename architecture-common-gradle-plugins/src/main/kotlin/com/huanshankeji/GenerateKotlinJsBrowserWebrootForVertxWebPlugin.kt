package com.huanshankeji

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class GenerateKotlinJsBrowserWebrootForVertxWebPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = extensions.create<Extension>("generateKotlinJsResources")


        // see: https://play.kotlinlang.org/hands-on/Full%20Stack%20Web%20App%20with%20Kotlin%20Multiplatform/04_Frontend_Setup

        val jsBrowserDistributionTask by lazy {
            tasks.getByPath(extension.webFrontendProjectPath.get() + ":jsBrowserDistribution")
        }
        val jsBrowserWebpack by lazy {
            tasks.getByPath(
                extension.webFrontendProjectPath.get() +
                        if (extension.production.get()) ":jsBrowserProductionWebpack" else ":jsBrowserDevelopmentWebpack"
            ) as KotlinWebpack
        }
        val copyJsBrowserDistributionToResourcesWebroot = "copyJsBrowserDistributionToResourcesWebroot"
        val browserDistributionResourcesDirectory = buildDir.resolve("browserDistributionResources")

        tasks.register<Copy>(copyJsBrowserDistributionToResourcesWebroot) {
            dependsOn(jsBrowserWebpack)
            dependsOn(jsBrowserDistributionTask) // needed for Gradle 8.0.1 whenever `production` is `true` or `false` // TODO: this may be a bug.
            from(jsBrowserWebpack.destinationDirectory)
            if (extension.production.get())
                include("*.html", "*.css", "*.js")
            into(browserDistributionResourcesDirectory.resolve("webroot"))
        }

        tasks.named<Copy>("processResources") {
            dependsOn(copyJsBrowserDistributionToResourcesWebroot)
        }

        sourceSets.main { resources.srcDir(browserDistributionResourcesDirectory) }
    }

    interface Extension {
        val webFrontendProjectPath: Property<String>
        val production: Property<Boolean>
    }
}