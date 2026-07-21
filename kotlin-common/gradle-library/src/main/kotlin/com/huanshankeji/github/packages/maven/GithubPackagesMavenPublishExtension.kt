package com.huanshankeji.github.packages.maven

import org.gradle.api.provider.Property

interface GithubPackagesMavenPublishExtension {
    val owner: Property<String>
    val repository: Property<String>
}
