plugins {
    id("huanshankeji-team-module-conventions")
    id("aligned-version-build-logic-conventions")
    id("org.gradle.kotlin.kotlin-dsl")
}

kotlin.jvmToolchain(17)

dependencies {
    api(project(":kotlin-common-gradle-library"))
}
