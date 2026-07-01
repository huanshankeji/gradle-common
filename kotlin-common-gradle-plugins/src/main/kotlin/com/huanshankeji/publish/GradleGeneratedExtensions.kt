package com.huanshankeji.publish

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension

// copied and adapted from generated methods

fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    extensions.configure("publishing", configure)
