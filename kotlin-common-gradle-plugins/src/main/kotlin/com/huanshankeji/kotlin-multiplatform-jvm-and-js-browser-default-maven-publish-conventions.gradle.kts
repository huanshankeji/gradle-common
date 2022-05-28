package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-jvm-and-js-browser-conventions")
    id("com.huanshankeji.default-maven-publish")
}

publishing.publications.withType<MavenPublication> {
    artifactId = "$defaultPrefixForPublishing-$artifactId"
}
