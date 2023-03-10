package com.huanshankeji

plugins {
    id("com.huanshankeji.kotlin-multiplatform-maven-publish-conventions")
    id("com.huanshankeji.sonatype-ossrh-publish")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

afterEvaluate {
    // TODO: support JVM targets of other names?
    //val jvmTargetNames = kotlin.targets.mapNotNull { if (it.platformType == KotlinPlatformType.jvm) it.name else null }

    publishing.publications.withType<MavenPublication> {
        //if (name in jvmTargetNames)
        if (name == "jvm")
            artifact(javadocJar)
    }
}
