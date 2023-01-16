package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
}

dependencies {
    implementation(platform(kotlin("bom")))

    testImplementation(kotlin("test"))
    with(commonDependencies.orgJunit.jupiter) {
        testImplementation(apiWithVersion())
        testRuntimeOnly(engineWithoutVersion())
    }

    with(commonDependencies.kotlinx.coroutines) {
        implementation(core())
        testImplementation(test())
    }
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

kotlin.sourceSets.all {
    languageSettings.defaultOptIns()
}
