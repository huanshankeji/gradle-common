package com.huanshankeji.team.gitversioning

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
