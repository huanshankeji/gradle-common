package com.huanshankeji.dokka

import com.huanshankeji.git.gitCommitHash

plugins {
    id("org.jetbrains.dokka")
}

interface DokkaConventionExtension {
    val sourceLinkRemoteUrlRoot: Property<String>
}

val extension = extensions.create<DokkaConventionExtension>("dokkaConvention")

dokka {
    pluginsConfiguration.html {
        footerMessage.convention(moduleVersion.zip(providers.gitCommitHash()) { version, hash ->
            "Version: $version | Commit: $hash"
        })
    }

    dokkaSourceSets.all {
        sourceLink {
            val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl(extension.sourceLinkRemoteUrlRoot.map { "$it/$projectRelativePath" })
            remoteLineSuffix.set("#L")
        }
    }
}
