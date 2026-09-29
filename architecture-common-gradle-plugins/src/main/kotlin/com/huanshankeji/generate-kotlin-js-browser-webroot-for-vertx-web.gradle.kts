package com.huanshankeji

interface Extension {
    val webFrontendProjectPath: Property<String>

    //val production: Property<Boolean>
    /**
     * Patterns of distribution files to include.
     * Defaults to including all files.
     *
     * @see PatternFilterable.include
     */
    val includes: ListProperty<String>

    val webRoot: Property<String>
}

val extension = extensions.create<Extension>("generateKotlinJsResources")

val browserDistributionResourcesDirectory = layout.buildDirectory.dir("browserDistributionResources")

val syncJsBrowserDistributionToResourcesWebroot = tasks.register<Sync>("syncJsBrowserDistributionToResourcesWebroot") {
    from(extension.webFrontendProjectPath.flatMap { path ->
        project(path).tasks.named("jsBrowserDistribution")
    })
    //if (extension.production.get())
    extension.includes.getOrNull()?.let { include(it) }
    into(browserDistributionResourcesDirectory.map { it.dir(extension.webRoot.getOrElse("webroot")) })
}

pluginManager.withPlugin("java") {
    sourceSets.main {
        resources.srcDir(files(browserDistributionResourcesDirectory).builtBy(syncJsBrowserDistributionToResourcesWebroot))
    }
}
