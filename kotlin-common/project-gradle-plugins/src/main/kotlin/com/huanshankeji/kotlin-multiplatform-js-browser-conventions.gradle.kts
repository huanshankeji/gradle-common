package com.huanshankeji

plugins {
    kotlin("multiplatform")
}

kotlin {
    js {
        //moduleName = fullNameWithRootProjectNameForFileSystem // deprecated and scheduled for removal in Kotlin 2.3
        browser()

        compilerOptions {
            target.set("es2015")
        }
    }
}
