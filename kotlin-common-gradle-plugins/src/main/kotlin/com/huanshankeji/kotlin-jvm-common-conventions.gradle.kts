package com.huanshankeji

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

// to prevent duplicate archive names and support Windows
base.archivesName.set(fullNameForFileSystem)
