// TODO consider moving/extracting this plugin out of this "team" module

package com.huanshankeji.team.dokka

import com.huanshankeji.git.workflow.conventionalGitCommitHashOrTag
import com.huanshankeji.team.github.defaultRepositoryName
import com.huanshankeji.team.github.githubRepositoryUrl

plugins {
    id("com.huanshankeji.dokka.dokka-convention")
}

interface GithubDokkaConventionExtension {
    val repositoryName: Property<String>
    val commitHashOrTag: Property<String>
}

extensions.create<GithubDokkaConventionExtension>("githubDokkaConvention").apply {
    repositoryName.convention(defaultRepositoryName())
    commitHashOrTag.convention(conventionalGitCommitHashOrTag())

    val sourceLinkRemoteUrlRoot = repositoryName.flatMap { repositoryName ->
        val repositoryUrl = githubRepositoryUrl(repositoryName)
        commitHashOrTag.map { commitOrTag ->
            "$repositoryUrl/blob/$commitOrTag"
        }
    }

    dokkaConvention {
        this.sourceLinkRemoteUrlRoot.set(sourceLinkRemoteUrlRoot)
    }
}
