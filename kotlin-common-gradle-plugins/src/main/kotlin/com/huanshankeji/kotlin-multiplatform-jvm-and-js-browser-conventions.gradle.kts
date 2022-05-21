package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
}

kotlin {
    jvm()
}

// to prevent duplicate archive names and support Windows
base.archivesName.set(fullNameForFileSystem)
