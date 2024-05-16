package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
}

kotlin {
    jvm()

    //androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    /*
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()
    */
}
