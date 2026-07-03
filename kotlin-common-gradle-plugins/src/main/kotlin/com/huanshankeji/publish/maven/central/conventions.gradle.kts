package com.huanshankeji.publish.maven.central

plugins {
    id("com.vanniktech.maven.publish")
}

/*
 * Applies Maven Central publishing and signs all publications.
 *
 * vanniktech's signAllPublications() already sets signing as not required for `-SNAPSHOT` versions.
 * To use different rules (for example dev-commit versions), configure signing requiredness after
 * applying this plugin, for example:
 *
 *     signing {
 *         setRequired(/* your Provider or deferred condition */)
 *     }
 */
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
}

// should probably require the Java toolchain version to be set too when using this plugin
