package com.huanshankeji.dokka

import com.huanshankeji.git.gitCommitHash
import com.huanshankeji.versionStringProvider
import org.gradle.api.Project
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters

plugins {
    id("org.jetbrains.dokka")
}

interface DokkaConventionExtension {
    val sourceLinkRemoteUrlRoot: Property<String>
}

val extension = extensions.create<DokkaConventionExtension>("dokkaConvention")

/*
 * The aggregating publication writes `moduleVersion` into the header.
 * `footerMessage` is rendered into each module's pages, so the same text shows
 * when this plugin is applied on the documented modules.
 */
val versionAndCommitHash = versionStringProvider().zip(providers.gitCommitHash()) { version, commitHash ->
    if (version == Project.DEFAULT_VERSION) "commit $commitHash" else "$version (commit $commitHash)"
}

dokka {
    moduleVersion.convention(versionAndCommitHash)
    pluginsConfiguration.withType<DokkaHtmlPluginParameters>().configureEach {
        footerMessage.convention(versionAndCommitHash)
    }
    dokkaSourceSets.all {
        sourceLink {
            val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl(extension.sourceLinkRemoteUrlRoot.map { "$it/$projectRelativePath" })
            remoteLineSuffix.set("#L")
        }
    }
}
