package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-maven-publish-conventions")
    id("com.huanshankeji.sonatype-ossrh-publish")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing.publications.withType<MavenPublication> {
    artifact(javadocJar)
}