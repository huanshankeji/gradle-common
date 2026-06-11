package com.huanshankeji

import org.gradle.api.provider.Property

interface GitVersionExtension {
    val baseVersion: Property<String>
}

val extension = extensions.create<GitVersionExtension>("gitVersion")

afterEvaluate {
    version = projectVersionFromGitProvider(extension.baseVersion.get()).get()
}
