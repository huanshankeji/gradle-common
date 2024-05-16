package com.huanshankeji

plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        moduleName = fullNameWithRootProjectNameForFileSystem
        browser()
    }
}
