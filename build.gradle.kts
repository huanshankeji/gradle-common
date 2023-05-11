tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

evaluationDependsOnChildren()
task("publishPluginProjectPlugins") {
    group = "plugin portal"

    val pluginProjects = subprojects.filter { it.name.endsWith("plugins") }
    pluginProjects.forEach { dependsOn(it.tasks.named("publishPlugins")) }
}
