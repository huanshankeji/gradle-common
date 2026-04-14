package com.huanshankeji

plugins {
    `maven-publish`
}

val extension = extensions.createMavenPublishConventionsExtension()

afterEvaluate {
    when (val artifactIdConfig = extension.artifactIdConfig.get()) {
        is MavenPublishConventionsArtifactIdConfig.DefaultUnchanged -> Unit
        is @Suppress("DEPRECATION") MavenPublishConventionsArtifactIdConfig.SubprojectNameConcatenated ->
            publishing.publications.withType<MavenPublication> {
                artifactId = "$defaultPrefixForPublishing-$artifactId"
            }

        is MavenPublishConventionsArtifactIdConfig.Custom ->
            publishing.publications.withType<MavenPublication> {
                artifactId = artifactIdConfig.artifactId
            }
    }
}
