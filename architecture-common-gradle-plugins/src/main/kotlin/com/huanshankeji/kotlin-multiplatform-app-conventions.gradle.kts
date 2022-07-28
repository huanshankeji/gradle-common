package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-conventions")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                //implementation(platform(kotlin("bom", kotlinVersion)))
                implementation(commonDependencies.kotlinx.coroutines.core())
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(commonDependencies.kotlinx.coroutines.test())
            }
        }

        all {
            languageSettings.defaultOptIns()
        }
    }
}
