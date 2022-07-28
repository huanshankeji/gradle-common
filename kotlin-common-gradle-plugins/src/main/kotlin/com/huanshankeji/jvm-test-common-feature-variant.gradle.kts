package com.huanshankeji

plugins {
    java
}

val testCommon = "testCommon"

sourceSets.register(testCommon) {
    val mainSourceSet = sourceSets["main"]
    compileClasspath += mainSourceSet.compileClasspath + mainSourceSet.output
    //runtimeClasspath +=
    //runtimeClasspath += mainSourceSet.runtimeClasspath + mainSourceSet.output
}

java.registerFeature(testCommon) {
    usingSourceSet(sourceSets[testCommon])
    withJavadocJar()
    withSourcesJar()
}
