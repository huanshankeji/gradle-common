package com.huanshankeji.team.github.packages.maven

import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE

plugins {
    id("com.huanshankeji.github.packages.maven.publish")
}

githubPackagesMavenPublish {
    owner.set(HUANSHANKEJI_IN_LOWERCASE)
    repository.set(rootProject.name)
}
