package com.huanshankeji.team.github

import org.gradle.api.Project

fun githubRepositoryUrl(repositoryName: String) =
    "https://github.com/huanshankeji/$repositoryName"

fun Project.defaultRepositoryName() =
    rootProject.name

fun Project.defaultRootProjectGithubRepositoryUrl() =
    githubRepositoryUrl(defaultRepositoryName())
