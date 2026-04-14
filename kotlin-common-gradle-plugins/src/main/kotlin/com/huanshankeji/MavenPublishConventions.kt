package com.huanshankeji

import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

// TODO consider deprecating this plugin and the depending plugins too

sealed class MavenPublishConventionsArtifactIdConfig {
    // Use the default artifact ID, which is the subproject name unchanged.
    object DefaultUnchanged : MavenPublishConventionsArtifactIdConfig()

    // Use a name created by concatenating the subproject names.
    @Deprecated("Adopting this config may cause potential bugs in Kotlin Multiplatform projects. See \"https://youtrack.jetbrains.com/issue/KT-54182\".") // This seems fixed.
    object SubprojectNameConcatenated : MavenPublishConventionsArtifactIdConfig()

    class Custom(val artifactId: String) : MavenPublishConventionsArtifactIdConfig()
}

interface MavenPublishConventionsExtension {
    val artifactIdConfig: Property<MavenPublishConventionsArtifactIdConfig>
}

fun ExtensionContainer.createMavenPublishConventionsExtension(): MavenPublishConventionsExtension =
    create<MavenPublishConventionsExtension>("mavenPublishConventions").also {
        it.artifactIdConfig.convention(MavenPublishConventionsArtifactIdConfig.DefaultUnchanged)
    }
