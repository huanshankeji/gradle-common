package com.huanshankeji.maven

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension

fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)
