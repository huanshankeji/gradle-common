package com.huanshankeji

import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.named

// copied and adapted from generated methods

val Project.sourceSets: SourceSetContainer
    get() = (this as ExtensionAware).extensions.getByName("sourceSets") as SourceSetContainer

fun SourceSetContainer.main(configuration: SourceSet.() -> Unit): NamedDomainObjectProvider<SourceSet> =
    named<SourceSet>("main", configuration)
