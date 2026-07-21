package com.huanshankeji.publish.maven.central

plugins {
    id("com.vanniktech.maven.publish")
}

/*
 * Applies Maven Central publishing and always configures signing for all publications
 * (The `vanniktech` plugin does not require signing for `-SNAPSHOT` versions).
 * If you need different signing rules, apply
 * `com.vanniktech.maven.publish` yourself and customize signing instead of using this plugin.
 */
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
