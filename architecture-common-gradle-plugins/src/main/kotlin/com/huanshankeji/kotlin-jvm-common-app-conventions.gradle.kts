package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
}

dependencies {
    implementation(platform(kotlin("bom")))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:${commonVersions.junitJupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation(commonDependencies.kotlinx.coroutines.core())
    testImplementation(commonDependencies.kotlinx.coroutines.test())
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
