package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
    `java-library`
    `maven-publish`
    id("com.huanshankeji.java-1-8-compatibility-publish-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = defaultFullNameForPublishing
            from(components["java"])
        }
    }
}
