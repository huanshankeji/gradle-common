package com.huanshankeji

plugins {
    kotlin("multiplatform")
}

kotlin {
    js {
        moduleName = fullNameWithRootProjectNameForFileSystem
        browser()
    }
}
