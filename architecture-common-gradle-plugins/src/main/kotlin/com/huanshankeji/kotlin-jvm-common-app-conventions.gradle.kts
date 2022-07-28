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

/*
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
*/

kotlin.sourceSets.all {
    languageSettings.defaultOptIns()
}
