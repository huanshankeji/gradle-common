package com.huanshankeji

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class GenerateKotlinJsBrowserWebrootForVertxWebPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val extension = extensions.create<Extension>("generateKotlinJsResources")

        afterEvaluate {
            val frontendProject = project(extension.webFrontendProjectPath.get())
            val jsBrowserDistributionTask = frontendProject.tasks.named("jsBrowserDistribution")
            /*val jsBrowserWebpack by lazy {
                tasks.getByPath(
                    extension.webFrontendProjectPath.get() +
                            if (extension.production.get()) ":jsBrowserProductionWebpack" else ":jsBrowserDevelopmentWebpack"
                ) as KotlinWebpack
            }*/
            val copyJsBrowserDistributionToResourcesWebroot = "copyJsBrowserDistributionToResourcesWebroot"
            val browserDistributionResourcesDirectory = layout.buildDirectory.get().dir("browserDistributionResources")

            tasks.register<Copy>(copyJsBrowserDistributionToResourcesWebroot) {
                dependsOn(jsBrowserDistributionTask)
                // TODO I didn't find a way to get this path using the DSL, so there may be a bug if this path is customized.
                from(frontendProject.layout.buildDirectory.get().dir("dist/js/productionExecutable"))
                //if (extension.production.get())
                extension.includes.getOrNull()?.let { include(it) }
                into(browserDistributionResourcesDirectory.dir("webroot"))
            }

            tasks.named<Copy>("processResources") {
                dependsOn(copyJsBrowserDistributionToResourcesWebroot)
            }
            /*
            When running a Maven publish task, the error occurs if this line is not added:
            > Task '...:sourcesJar' uses this output of task '...:copyJsBrowserDistributionToResourcesWebroot' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.

            TODO This is probably a bug. remove this line when it's fixed.
             */
            tasks.named("sourcesJar") { dependsOn("copyJsBrowserDistributionToResourcesWebroot") }

            sourceSets.main { resources.srcDir(browserDistributionResourcesDirectory) }
        }
    }

    interface Extension {
        val webFrontendProjectPath: Property<String>

        //val production: Property<Boolean>
        /**
         * Patterns of distribution files to include.
         * Defaults to including all files.
         *
         * @see PatternFilterable.include
         */
        val includes: ListProperty<String>
    }
}