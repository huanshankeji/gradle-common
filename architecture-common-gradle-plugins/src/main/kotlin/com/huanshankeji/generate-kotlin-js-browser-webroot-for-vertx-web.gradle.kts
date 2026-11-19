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

private val jsBrowserDistribution = extension.webFrontendProjectPath.flatMap {
    project(it).tasks.named<Sync>("jsBrowserDistribution")
}
/*val jsBrowserWebpack by lazy {
    tasks.getByPath(
        extension.webFrontendProjectPath.get() +
                if (extension.production.get()) ":jsBrowserProductionWebpack" else ":jsBrowserDevelopmentWebpack"
    ) as KotlinWebpack
}*/
private val browserDistributionResourcesDirectory = layout.buildDirectory.dir("browserDistributionResources")

private val syncJsBrowserDistributionToResourcesWebroot = tasks.register<Sync>("syncJsBrowserDistributionToResourcesWebroot") {
    val distributionIncludes = extension.includes
    //dependsOn(jsBrowserDistribution)
    from(jsBrowserDistribution.map {
        it.outputs.files.asFileTree.matching {
            //if (extension.production.get())
            include(distributionIncludes.getOrElse(emptyList()))
        }
    })
    into(browserDistributionResourcesDirectory.zip(extension.webRoot.orElse("webroot")) { directory, webRoot ->
        directory.dir(webRoot)
    })
}

sourceSets.named("main").configure {
    resources.srcDir(files(browserDistributionResourcesDirectory).builtBy(syncJsBrowserDistributionToResourcesWebroot))
}
