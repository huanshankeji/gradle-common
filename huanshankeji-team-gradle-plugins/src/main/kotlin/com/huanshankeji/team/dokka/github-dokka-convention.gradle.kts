package com.huanshankeji.team.dokka

import com.huanshankeji.team.github.defaultRepositoryName
import com.huanshankeji.team.github.githubRepositoryUrl

plugins {
    id("com.huanshankeji.dokka.dokka-convention")
}

val extension = extensions.create<GithubDokkaConventionExtension>("githubDokkaConvention")

interface GithubDokkaConventionExtension {
    val repositoryName: Property<String>
    val commitOrTag: Property<String>
}

dokkaConvention {
    val repositoryUrl = githubRepositoryUrl(extension.repositoryName.getOrElse(defaultRepositoryName()))
    val commitOrTag = extension.commitOrTag.getOrElse("v${version}")
    sourceLinkRemoteUrlRoot.set("$repositoryUrl/blob/$commitOrTag")
}
