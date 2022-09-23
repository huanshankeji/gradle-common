plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    maven {
        url = uri("https://maven.pkg.github.com/huanshankeji/gradle-common")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:2.3.3") // It seems this version has to be used for Gradle 7.5.
    implementation("com.gradle.publish:plugin-publish-plugin:1.0.0-rc-2")
    // This is a bootstrapping dependency (cross-version self-dependency). Try not to update its version unless necessary.
    implementation("com.huanshankeji.team:gradle-plugins:0.2.0")
}
