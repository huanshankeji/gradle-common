package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-js-browser-conventions")
}

kotlin {
    jvm()
    js(IR) { browser() }
}
