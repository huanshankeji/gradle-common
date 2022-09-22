// Maybe it's better to use Maven publish and Maven repositories (Maven Central)
plugins {
    kotlin("jvm")
    id("com.gradle.plugin-publish")
}

repositories {
    gradlePluginPortal()
}

group = "com.huanshankeji"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}

// copied from https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-with-a-personal-access-token
// TODO: use the bootstrapping dependency in the next version
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/huanshankeji/${rootProject.name}")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}
