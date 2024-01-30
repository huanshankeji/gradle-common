package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
    `java-library`
}
apply<MavenPublishConventionsPlugin>()

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

afterEvaluate {
    require(java.toolchain.languageVersion.isPresent) { "Specify an explicit `java.toolchain.languageVersion` (or via `kotlin.jvmToolchain()`) when publishing a JVM library." }
}
