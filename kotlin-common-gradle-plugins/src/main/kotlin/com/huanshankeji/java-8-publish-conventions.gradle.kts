package com.huanshankeji

plugins {
    java
}

java {
    // TODO: consider moving these 2 lines into "kotlin-jvm-library-maven-publish-conventions".
    withJavadocJar()
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
