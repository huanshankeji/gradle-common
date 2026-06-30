package com.huanshankeji.team

import com.huanshankeji.team.maven.PublicOpenSourceDependencyRepositoriesExtension

val extension = extensions.create(
    "publicOpenSourceDependencyRepositories",
    PublicOpenSourceDependencyRepositoriesExtension::class.java,
)

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        extension.repositories = this
        extension.settings = settings
    }
}
