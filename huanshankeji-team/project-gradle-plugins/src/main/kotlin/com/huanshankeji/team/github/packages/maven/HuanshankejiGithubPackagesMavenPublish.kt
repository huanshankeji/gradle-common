package com.huanshankeji.team.github.packages.maven

import com.huanshankeji.github.packages.maven.GithubPackagesMavenPublishExtension
import com.huanshankeji.team.HUANSHANKEJI_IN_LOWERCASE
import org.gradle.api.Project

fun Project.configureHuanshankejiGithubPackagesMavenPublish() {
    extensions.configure<GithubPackagesMavenPublishExtension>("githubPackagesMavenPublish") {
        owner.set(HUANSHANKEJI_IN_LOWERCASE)
        repository.set(rootProject.name)
    }
}
