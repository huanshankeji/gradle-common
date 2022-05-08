package com.huanshankeji

import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories

plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        val kotlinCoroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                //implementation(platform(kotlin("bom", com.huanshankeji.getKotlinVersion)))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
            }
        }

        all {
            languageSettings.projectOptIns()
        }
    }
}
