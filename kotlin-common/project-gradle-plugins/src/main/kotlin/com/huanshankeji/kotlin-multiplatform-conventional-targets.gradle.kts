package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
}

logger.warn(
    "WARNING: 'com.huanshankeji.kotlin-multiplatform-conventional-targets' is deprecated and will be removed in a future release. " +
            "Configure JVM and iOS targets in the project, and apply 'com.huanshankeji.kotlin-multiplatform-js-browser-conventions' for the JS browser target."
)

kotlin {
    jvm()

    //androidTarget()

    //iosX64()
    iosArm64()
    iosSimulatorArm64()

    /*
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()
    */
}
