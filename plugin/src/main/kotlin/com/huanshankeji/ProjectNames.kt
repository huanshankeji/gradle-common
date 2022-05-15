package com.huanshankeji

import org.gradle.api.Project

val Project.fullNameForFileSystem
    get() = path.removePrefix(":").replace(":", "--")

val Project.fullNameWithRootProjectNameForFileSystem
    get() = rootProject.name + "--" + fullNameForFileSystem

val Project.defaultFullNameForPublishing
    get() = (rootProject.name + path).replace(":", "-")

val Project.defaultPrefixForPublishing
    get() = (rootProject.name +
            path.lastIndexOf(':').let { if (it == -1) "" else path.substring(0, it) })
        .replace(":", "-")
