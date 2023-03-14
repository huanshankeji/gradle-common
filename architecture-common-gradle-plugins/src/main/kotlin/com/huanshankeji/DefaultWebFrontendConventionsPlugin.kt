package com.huanshankeji

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.compose.ComposePlugin.Dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

class DefaultWebFrontendConventionsPlugin : Plugin<Project> {
    interface Extension {
        val htmlGenerationProjectPath: Property<String>
    }

    override fun apply(target: Project) = target.run {
        plugins.apply("com.huanshankeji.kotlin-multiplatform-js-browser-app-conventions")
        plugins.apply("org.jetbrains.compose")


        val extension = extensions.create<Extension>("defaultWebFrontendConventions")

        repositories {
            google()
            mavenCentral()
            maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }

        kotlin {
            js(IR) {
                browser {
                    commonWebpackConfig {
                        outputFileName = "app.js"
                    }
                    testTask {
                        testLogging.showStandardStreams = true
                        useKarma {
                            useChromeHeadless()
                            useFirefox()
                        }
                    }
                }
                binaries.executable()
            }
            sourceSets {
                named("jsMain") {
                    dependencies {
                        implementation(compose.web.core)
                        implementation(compose.runtime)
                    }
                }
            }
        }


        val generatedResourcesFile = buildDir.resolve("generatedResources")

        tasks.named("jsProcessResources") {
            val htmlGenerationRun = tasks.getByPath(
                extension.htmlGenerationProjectPath.getOrElse(project.path + ":html-generation") + ":run"
            ) as JavaExec
            htmlGenerationRun.args(generatedResourcesFile.absolutePath)
            dependsOn(htmlGenerationRun)
        }

        kotlin.sourceSets { get("jsMain").resources.srcDir(generatedResourcesFile) }
    }


    // copied and adapted from generated code

    fun Project.kotlin(configure: Action<KotlinMultiplatformExtension>) =
        extensions.configure("kotlin", configure)

    val Project.kotlin: KotlinMultiplatformExtension
        get() = extensions.getByName("kotlin") as KotlinMultiplatformExtension

    fun KotlinMultiplatformExtension.sourceSets(configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>): Unit =
        (this as ExtensionAware).extensions.configure("sourceSets", configure)

    val KotlinMultiplatformExtension.compose: Dependencies
        get() = (this as ExtensionAware).extensions.getByName("compose") as Dependencies
}