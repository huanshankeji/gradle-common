plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:2.3.3") // It seems this version has to be used for Gradle 7.5.
    implementation("com.gradle.publish:plugin-publish-plugin:1.0.0-rc-2")
}
