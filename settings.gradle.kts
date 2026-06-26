rootProject.name = "gradle-common"

include("kotlin-common-gradle-plugins")
include("huanshankeji-team-gradle-plugins")
project(":huanshankeji-team-gradle-plugins").name = "gradle-plugins"
include("architecture-common-gradle-plugins")
include("common-gradle-dependencies")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        //mavenLocal() // no longer needed since no bootstrapping dependencies
        gradlePluginPortal()
        //mavenCentral() // origininall added for Dokka no longer needed as tested
    }
}
