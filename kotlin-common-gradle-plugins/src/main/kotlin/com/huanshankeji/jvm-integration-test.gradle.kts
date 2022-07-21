package com.huanshankeji

plugins {
    java
}

// code copied and adapted from https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests

sourceSets {
    create("intTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val intTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
}

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath
    shouldRunAfter("test")

    // copied from "kotlin-jvm-common-app-conventions"
    useJUnitPlatform()
}

tasks.check { dependsOn(integrationTest) }
