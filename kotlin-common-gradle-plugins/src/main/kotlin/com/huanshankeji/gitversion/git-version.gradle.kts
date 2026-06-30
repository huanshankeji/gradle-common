package com.huanshankeji.gitversion

interface GitVersionExtension {
    val baseVersion: Property<String>
}

val extension = extensions.create<GitVersionExtension>("gitVersion")

afterEvaluate {
    version = projectVersionFromGitProvider(extension.baseVersion.get()).get()
}
