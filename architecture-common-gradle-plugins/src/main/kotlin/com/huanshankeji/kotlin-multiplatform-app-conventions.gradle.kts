package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-conventions")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                //implementation(platform(kotlin("bom", kotlinVersion)))
                implementation(CommonDependencies.Kotlinx.Coroutines.core())
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(CommonDependencies.Kotlinx.Coroutines.test())
            }
        }

        all {
            languageSettings.defaultOptIns()
        }
    }
}
