package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
    id("org.jetbrains.compose")
}

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
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}


val generatedResourcesFile = buildDir.resolve("generatedResources")

tasks.named("jsProcessResources") {
    val htmlGenerationRun = tasks.getByPath(project.path + ":html-generation:run") as JavaExec
    htmlGenerationRun.args(generatedResourcesFile.absolutePath)
    dependsOn(htmlGenerationRun)
}

kotlin.sourceSets { get("jsMain").resources.srcDir(generatedResourcesFile) }
