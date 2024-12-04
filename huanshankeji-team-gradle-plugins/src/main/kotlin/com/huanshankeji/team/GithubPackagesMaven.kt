package com.huanshankeji.team

import com.huanshankeji.repositoriesAddGithubPackagesMavenRegistry
import org.gradle.api.Project

fun Project.repositoriesAddTeamGithubPackagesMavenRegistry(repository: String) =
    repositoriesAddGithubPackagesMavenRegistry(HUANSHANKEJI_IN_LOWERCASE, repository)
