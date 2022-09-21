package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
    `java-library`
    `maven-publish`
}

java {
    withJavadocJar()
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = defaultFullNameForPublishing
            from(components["java"])
        }
    }
}
