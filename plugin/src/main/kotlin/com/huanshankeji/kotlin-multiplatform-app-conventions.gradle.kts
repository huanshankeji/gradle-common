package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-conventions")
}

kotlin {
    sourceSets {
        val kotlinCoroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                //implementation(platform(kotlin("bom", kotlinVersion)))
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
            languageSettings.defaultOptIns()
        }
    }
}
