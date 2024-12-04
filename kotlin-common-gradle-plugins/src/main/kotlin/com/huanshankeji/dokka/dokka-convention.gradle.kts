package com.huanshankeji.dokka

plugins {
    id("org.jetbrains.dokka")
}

interface DokkaConventionExtension {
    val sourceLinkRemoteUrlRoot: Property<String>
}

val extension = extensions.create<DokkaConventionExtension>("dokkaConvention")

dokka {
    dokkaSourceSets.all {
        sourceLink {
            val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl(extension.sourceLinkRemoteUrlRoot.map { "$it/$projectRelativePath" })
            remoteLineSuffix.set("#L")
        }
    }
}