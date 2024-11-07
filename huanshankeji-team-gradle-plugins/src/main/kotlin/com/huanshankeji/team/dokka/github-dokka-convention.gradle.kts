package com.huanshankeji.team.dokka

import com.huanshankeji.team.github.defaultRepositoryName
import com.huanshankeji.team.github.githubRepositoryUrl

plugins {
    id("com.huanshankeji.dokka.dokka-convention")
}

interface GithubDokkaConventionExtension {
    val repositoryName: Property<String>
    val commitOrTag: Property<String>
}

extensions.create<GithubDokkaConventionExtension>("githubDokkaConvention").apply {
    repositoryName.convention(defaultRepositoryName())
    commitOrTag.convention("v${version}")

    val sourceLinkRemoteUrlRoot = repositoryName.flatMap { repositoryName ->
        val repositoryUrl = githubRepositoryUrl(repositoryName)
        commitOrTag.map { commitOrTag ->
            "$repositoryUrl/blob/$commitOrTag"
        }
    }

    dokkaConvention {
        this.sourceLinkRemoteUrlRoot.set(sourceLinkRemoteUrlRoot)
    }
}
