package com.huanshankeji

import org.gradle.api.Project

val Project.fullNameForFileSystem
    get() = path.removePrefix(":").replace(":", "--")

val Project.fullNameWithRootProjectNameForFileSystem
    get() = rootProject.name + "--" + fullNameForFileSystem
