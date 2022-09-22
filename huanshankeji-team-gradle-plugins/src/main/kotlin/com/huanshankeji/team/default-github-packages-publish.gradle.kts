package com.huanshankeji.team

plugins {
    id("com.huanshankeji.team.github-packages-publish")
}

githubPackagesPublish {
    repository.set(rootProject.name)
}
