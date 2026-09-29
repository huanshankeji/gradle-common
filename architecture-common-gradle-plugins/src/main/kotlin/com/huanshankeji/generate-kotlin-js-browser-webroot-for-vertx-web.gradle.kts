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

private fun registerWebrootSync() {
    val browserDistributionResourcesDirectory = layout.buildDirectory.dir("browserDistributionResources")

    val syncJsBrowserDistributionToResourcesWebroot = tasks.register<Sync>("syncJsBrowserDistributionToResourcesWebroot") {
        val frontendProject = project(extension.webFrontendProjectPath.get())
        val jsBrowserDistribution = frontendProject.tasks.named<Sync>("jsBrowserDistribution")
        /*val jsBrowserWebpack by lazy {
            tasks.getByPath(
                extension.webFrontendProjectPath.get() +
                        if (extension.production.get()) ":jsBrowserProductionWebpack" else ":jsBrowserDevelopmentWebpack"
            ) as KotlinWebpack
        }*/
        //dependsOn(jsBrowserDistribution)
        from(jsBrowserDistribution)
        //if (extension.production.get())
        extension.includes.getOrNull()?.let { include(it) }
        into(extension.webRoot.orElse("webroot").flatMap { webRoot ->
            browserDistributionResourcesDirectory.map { it.dir(webRoot) }
        })
    }

    pluginManager.withPlugin("java") {
        sourceSets.main {
            resources.srcDir(files(browserDistributionResourcesDirectory).builtBy(syncJsBrowserDistributionToResourcesWebroot))
        }
    }
}

registerWebrootSync()
