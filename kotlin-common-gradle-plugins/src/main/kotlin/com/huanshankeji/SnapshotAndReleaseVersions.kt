package com.huanshankeji

import org.gradle.api.Project

fun Project.isSnapshotVersion() =
    version.toString().endsWith("SNAPSHOT")
