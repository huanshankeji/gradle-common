plugins {
    id("org.gradle.kotlin.kotlin-dsl")
    id("com.huanshankeji.team.with-group")

    id("com.gradle.plugin-publish")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
}

repositories {
    mavenLocal() // comment out when not needed
    gradlePluginPortal()
}

kotlin.jvmToolchain(17)


gradlePlugin {
    website.set(GITHUB_URL)
    vcsUrl.set(GITHUB_GIT_URL)
    plugins.all { tags.set(listOf("kotlin", "kotlin-multiplatform")) }
}
