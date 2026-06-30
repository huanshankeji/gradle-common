package com.huanshankeji

val extension = extensions.create<GitVersionExtension>("gitVersion")

version = projectVersionFromGitProvider(extension.baseVersion).asLazyProjectVersion()
