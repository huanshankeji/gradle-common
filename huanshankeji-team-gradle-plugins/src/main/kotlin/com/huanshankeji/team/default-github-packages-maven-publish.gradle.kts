package com.huanshankeji.team

plugins {
    id("com.huanshankeji.team.github-packages-maven-publish")
}

githubPackagesPublish {
    repository.set(rootProject.name)
}
