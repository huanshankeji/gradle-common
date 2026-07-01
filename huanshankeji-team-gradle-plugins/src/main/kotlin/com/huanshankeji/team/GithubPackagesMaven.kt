package com.huanshankeji.team

import com.huanshankeji.repositoriesAddGithubPackagesMavenRegistry
import org.gradle.api.Project

@Deprecated(
    "Use the context parameter version instead.", // TODO
)
fun Project.repositoriesAddTeamGithubPackagesMavenRegistry(repository: String) =
    repositoriesAddGithubPackagesMavenRegistry(HUANSHANKEJI_IN_LOWERCASE, repository)
