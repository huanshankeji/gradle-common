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
}
val intTestRuntimeOnly by configurations.getting

configurations["intTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

// commented out so the test library and version are not fixed
/*
dependencies {
    intTestImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    intTestRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
*/


val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath
    shouldRunAfter("test")

    useJUnitPlatform()

    testLogging {
        events("passed")
    }
}

tasks.check { dependsOn(integrationTest) }
