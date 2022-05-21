plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:2.1.7") // It seems this version has to be used for Gradle 7.4.2.
    implementation("com.gradle.publish:plugin-publish-plugin:1.0.0-rc-2")
}
