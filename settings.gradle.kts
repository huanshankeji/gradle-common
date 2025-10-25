rootProject.name = "gradle-common"

include("kotlin-common-gradle-plugins")
include("huanshankeji-team-gradle-plugins")
project(":huanshankeji-team-gradle-plugins").name = "gradle-plugins"
include("architecture-common-gradle-plugins")
include("common-gradle-dependencies")

// for Dokka
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
