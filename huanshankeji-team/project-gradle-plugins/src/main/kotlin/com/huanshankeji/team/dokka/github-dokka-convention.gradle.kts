// TODO consider moving/extracting this plugin out of this "team" module

package com.huanshankeji.team.dokka

import com.huanshankeji.git.workflow.conventionalGitRef
import com.huanshankeji.team.github.defaultRepositoryName
import com.huanshankeji.team.github.githubRepositoryUrl

plugins {
    id("com.huanshankeji.dokka.dokka-convention")
}

interface GithubDokkaConventionExtension {
    val repositoryName: Property<String>

    /** Git ref for Dokka source links: either a commit hash or a tag. */
    val gitRef: Property<String>

    @Deprecated("Renamed to `gitRef`.", ReplaceWith("gitRef"))
    val commitOrTag: Property<String>
        get() = gitRef
}

extensions.create<GithubDokkaConventionExtension>("githubDokkaConvention").apply {
    repositoryName.convention(defaultRepositoryName())
    gitRef.convention(conventionalGitRef())

    val sourceLinkRemoteUrlRoot = repositoryName.flatMap { repositoryName ->
        val repositoryUrl = githubRepositoryUrl(repositoryName)
        gitRef.map { gitRef ->
            "$repositoryUrl/blob/$gitRef"
        }
    }

    dokkaConvention {
        this.sourceLinkRemoteUrlRoot.set(sourceLinkRemoteUrlRoot)
    }
}
