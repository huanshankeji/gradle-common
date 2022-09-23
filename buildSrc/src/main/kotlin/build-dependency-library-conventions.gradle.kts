plugins {
    kotlin("jvm")
    id("com.huanshankeji.team.with-group")
    id("com.huanshankeji.team.github-packages-publish")
}

repositories {
    gradlePluginPortal()
}

// copied from "java-1-8-compatibility-publish-conventions.gradle.kts"
java {
    withJavadocJar()
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

githubPackagesPublish {
    repository.set(rootProject.name)
}
