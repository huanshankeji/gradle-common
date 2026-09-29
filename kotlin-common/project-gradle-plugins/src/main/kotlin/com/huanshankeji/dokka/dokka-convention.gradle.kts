package com.huanshankeji.dokka

import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.versionStringProvider
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters

plugins {
    id("org.jetbrains.dokka")
}

interface DokkaConventionExtension {
    val sourceLinkRemoteUrlRoot: Property<String>
}

val extension = extensions.create<DokkaConventionExtension>("dokkaConvention")

dokka {
    // Lazy: version is often set after this plugin is applied.
    moduleVersion.convention(
        versionStringProvider().zip(providers.gitCommitHash()) { version, hash ->
            "$version ($hash)"
        }
    )
    pluginsConfiguration.named<DokkaHtmlPluginParameters>("html") {
        footerMessage.convention(moduleVersion)
    }

    dokkaSourceSets.all {
        sourceLink {
            val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl(extension.sourceLinkRemoteUrlRoot.map { "$it/$projectRelativePath" })
            remoteLineSuffix.set("#L")
        }
    }
}
