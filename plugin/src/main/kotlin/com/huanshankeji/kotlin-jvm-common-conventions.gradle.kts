package com.huanshankeji

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

val junitJupiterVersion: String by project
val kotlinCoroutinesVersion: String by project
dependencies {
    implementation(platform(kotlin("bom")))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

// to prevent duplicate archive names and support Windows
base.archivesName.set(fullNameForFileSystem)

kotlin.sourceSets.all {
    languageSettings.defaultOptIns()
}
