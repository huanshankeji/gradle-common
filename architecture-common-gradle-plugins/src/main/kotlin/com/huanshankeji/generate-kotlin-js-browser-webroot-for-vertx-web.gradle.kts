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
    /*
    When running a Maven publish task, the error occurs if this line is not added:
    > Task '...:sourcesJar' uses this output of task '...:copyJsBrowserDistributionToResourcesWebroot' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.

    TODO This is probably a bug. remove this line when it's fixed.
     */
    tasks.named("sourcesJar") { dependsOn(syncJsBrowserDistributionToResourcesWebroot) }

    sourceSets.main { resources.srcDir(browserDistributionResourcesDirectory) }
}
