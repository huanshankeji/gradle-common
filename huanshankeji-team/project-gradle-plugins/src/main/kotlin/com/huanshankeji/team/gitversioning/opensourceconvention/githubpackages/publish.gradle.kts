package com.huanshankeji.team.gitversioning.opensourceconvention.githubpackages

import com.huanshankeji.team.github.packages.maven.configureHuanshankejiGithubPackagesMavenPublish

plugins {
    id("com.huanshankeji.gitversioning.opensourceconvention.githubpackages.publish")
}

configureHuanshankejiGithubPackagesMavenPublish()
