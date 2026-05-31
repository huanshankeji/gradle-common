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

afterEvaluate {
    val frontendProject = project(extension.webFrontendProjectPath.get())
    val jsBrowserDistribution by frontendProject.tasks.getting(Sync::class)
    /*val jsBrowserWebpack by lazy {
        tasks.getByPath(
            extension.webFrontendProjectPath.get() +
                    if (extension.production.get()) ":jsBrowserProductionWebpack" else ":jsBrowserDevelopmentWebpack"
        ) as KotlinWebpack
    }*/
    val browserDistributionResourcesDirectory = layout.buildDirectory.get().dir("browserDistributionResources")

    val syncJsBrowserDistributionToResourcesWebroot by tasks.registering(Sync::class) {
        //dependsOn(jsBrowserDistribution)
        from(jsBrowserDistribution)
        //if (extension.production.get())
        extension.includes.getOrNull()?.let { include(it) }
        into(browserDistributionResourcesDirectory.dir(extension.webRoot.getOrElse("webroot")))
    }

    tasks.named<Copy>("processResources") {
        dependsOn(syncJsBrowserDistributionToResourcesWebroot)
    }

    tasks.configureEach {
        if (name == "sourcesJar") {
            dependsOn(syncJsBrowserDistributionToResourcesWebroot)
        }
    }

    sourceSets.main { resources.srcDir(browserDistributionResourcesDirectory) }
}
