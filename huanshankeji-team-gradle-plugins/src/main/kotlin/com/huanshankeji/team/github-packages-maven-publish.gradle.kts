package com.huanshankeji.team

plugins {
    id("com.huanshankeji.github-packages-maven-publish")
}

githubPackagesPublish {
    owner.set(HUANSHANKEJI_IN_LOWERCASE)
}
