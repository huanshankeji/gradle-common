package com.huanshankeji

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType

// TODO refactor into a script plugin
class MavenPublishConventionsPlugin : Plugin<Project> {
    sealed class ArtifactIdConfig {
        // Use the default artifact ID, which is the subproject name unchanged.
        object DefaultUnchanged : ArtifactIdConfig()

        // Use a name created by concatenating the subproject names.
        @Deprecated("Adopting this config may cause potential bugs in Kotlin Multiplatform projects. See \"https://youtrack.jetbrains.com/issue/KT-54182\".")
        object SubprojectNameConcatenated : ArtifactIdConfig()

        class Custom(val artifactId: String) : ArtifactIdConfig()
    }

    abstract class Extension {
        abstract val artifactIdConfig: Property<ArtifactIdConfig>

        init {
            @Suppress("LeakingThis")
            artifactIdConfig.convention(ArtifactIdConfig.DefaultUnchanged)
        }
    }

    override fun apply(project: Project) = project.run {
        plugins.apply("maven-publish")

        val extension = extensions.create<Extension>("mavenPublishConventions")

        afterEvaluate {
            when (val artifactIdConfig = extension.artifactIdConfig.get()) {
                is ArtifactIdConfig.DefaultUnchanged -> Unit
                is @Suppress("DEPRECATION") ArtifactIdConfig.SubprojectNameConcatenated ->
                    publishing.publications.withType<MavenPublication> {
                        artifactId = "$defaultPrefixForPublishing-$artifactId"
                    }

                is ArtifactIdConfig.Custom ->
                    publishing.publications.withType<MavenPublication> {
                        artifactId = artifactIdConfig.artifactId
                    }
            }
        }
    }

    // copied and adapted from generated code
    val Project.publishing: PublishingExtension
        get() = (this as ExtensionAware).extensions.getByName("publishing") as PublishingExtension
}