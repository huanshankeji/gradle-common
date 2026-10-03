package com.huanshankeji

// This plugin is deprecated and can be removed directly in the future.

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
}

logger.warn(
    "WARNING: 'com.huanshankeji.kotlin-multiplatform-conventional-targets' is deprecated and will be removed in a future release. " +
            "Please migrate to 'com.huanshankeji.kotlin-multiplatform-js-browser-conventions' and declare JVM and iOS targets in the project instead."
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
