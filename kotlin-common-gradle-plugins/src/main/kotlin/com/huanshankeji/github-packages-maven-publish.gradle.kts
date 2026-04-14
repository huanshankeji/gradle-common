package com.huanshankeji

plugins {
    `maven-publish`
}

interface Extension {
    val owner: Property<String>
    val repository: Property<String>
}

val extension = extensions.create<Extension>("githubPackagesPublish")

afterEvaluate {
    publishingRepositoriesAddGithubPackagesMavenRepository(
        owner = extension.owner.get(),
        repository = extension.repository.get()
    )
}
