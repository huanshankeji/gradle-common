package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-jvm-and-js-browser-conventions")
    id("com.huanshankeji.default-maven-publish")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        withType<MavenPublication> {
            artifactId = "$defaultPrefixForPublishing-$artifactId"
            artifact(javadocJar)
        }
    }
}
