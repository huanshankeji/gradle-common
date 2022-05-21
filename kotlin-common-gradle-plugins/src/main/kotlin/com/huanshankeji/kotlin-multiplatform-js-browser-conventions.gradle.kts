package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-conventions")
}

kotlin {
    js(IR) {
        moduleName = fullNameWithRootProjectNameForFileSystem
        browser()
    }
}
