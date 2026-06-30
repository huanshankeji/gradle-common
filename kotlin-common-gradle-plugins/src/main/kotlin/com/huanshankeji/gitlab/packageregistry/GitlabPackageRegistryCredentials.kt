package com.huanshankeji.gitlab.packageregistry

import com.huanshankeji.maven.findStringProperty
import org.gradle.api.Project

fun Project.gitlabPackageRegistryPrivateToken(): String? =
    findStringProperty("gitLabPrivateToken")
