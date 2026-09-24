package com.huanshankeji

// This plugin is deprecated and can be removed directly in the future.

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

logger.warn(
    "WARNING: 'com.huanshankeji.default-web-frontend-conventions' is deprecated and will be removed in a future release. " +
            "Please migrate to 'com.huanshankeji.web-frontend-conventions' instead."
)

interface Extension {
    val htmlGenerationProjectPath: Property<String>
}

val extension = extensions.create<Extension>("defaultWebFrontendConventions")

kotlin {
    js {
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
        jsMain {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }
    }
}


val generatedResourcesFile = layout.buildDirectory.dir("generatedResources").get().asFile

tasks.named("jsProcessResources") {
    val htmlGenerationRun = tasks.getByPath(
        extension.htmlGenerationProjectPath.getOrElse(project.path + ":html-generation") + ":run"
    ) as JavaExec
    htmlGenerationRun.args(generatedResourcesFile.absolutePath)
    dependsOn(htmlGenerationRun)
}

kotlin.sourceSets { get("jsMain").resources.srcDir(generatedResourcesFile) }
