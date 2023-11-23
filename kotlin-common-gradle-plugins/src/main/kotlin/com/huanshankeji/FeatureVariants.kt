package com.huanshankeji

import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.kotlin.dsl.get

fun JavaPluginExtension.registerFeatureVariantWithNewSourceSet(
    featureVariantName: String, sourceSetName: String = featureVariantName
) {
    sourceSets.register(sourceSetName) {
        val mainSourceSet = sourceSets["main"]
        compileClasspath += mainSourceSet.compileClasspath + mainSourceSet.output // TODO this may not be always necessary
        //runtimeClasspath +=
        //runtimeClasspath += mainSourceSet.runtimeClasspath + mainSourceSet.output
    }

    registerFeatureVariantWithSourceSet(featureVariantName, sourceSets[sourceSetName])
}

fun JavaPluginExtension.registerFeatureVariantWithSourceSet(
    featureVariantName: String, sourceSet: SourceSet
) = registerFeature(featureVariantName) {
    usingSourceSet(sourceSet)
    withJavadocJar()
    withSourcesJar()
}
