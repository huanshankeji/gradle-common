// Maybe it's better to use Maven publish and Maven repositories (Maven Central)
plugins {
    kotlin("jvm")
    id("com.gradle.plugin-publish")
}

repositories {
    gradlePluginPortal()
}

group = "com.huanshankeji"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
