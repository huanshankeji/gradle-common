package com.huanshankeji

plugins {
    // TODO: temporarily not using kotlin-jvm-library-conventions before architecture-specific logic is separated
    kotlin("jvm")
    `java-library`
    id("com.huanshankeji.default-maven-publish")
}

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = defaultFullNameForPublishing
            from(components["java"])
        }
    }
}
