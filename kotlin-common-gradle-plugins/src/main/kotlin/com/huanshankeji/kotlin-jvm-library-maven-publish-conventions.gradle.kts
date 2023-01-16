package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-jvm-common-conventions")
    `java-library`
    id("com.huanshankeji.java-8-publish-conventions")
}
apply<MavenPublishConventionsPlugin>()

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
