package com.huanshankeji.team

import com.huanshankeji.mavenGithubPackagesRepository
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

context(project: Project)
fun RepositoryHandler.mavenTeamGithubPackagesRepository(repository: String): MavenArtifactRepository =
    mavenGithubPackagesRepository(owner = HUANSHANKEJI_IN_LOWERCASE, repository = repository)
