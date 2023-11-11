plugins {
    `kotlin-dsl`
    // Gradle 8.1.1's dependent Kotlin version is 1.8.10.
    //kotlin("jvm") version "1.8.10"
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
    /*
    // see https://kotlinlang.org/docs/whatsnew18.html#resolution-of-kotlin-gradle-plugins-transitive-dependencies
    // This workaround somehow doesn't work. Maybe wait for https://youtrack.jetbrains.com/issue/KT-54691/Kotlin-Gradle-Plugin-libraries-alignment-platform to be fixed.
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-sam-with-receiver:1.8.0")
    }
    */
    //implementation(kotlin("gradle-plugin", "1.8.10")) // for Compose 1.3.1
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:4.1.2") // This version has to be used for Gradle 8.4.

    implementation("com.gradle.publish:plugin-publish-plugin:1.2.0")

    // This is a bootstrapping dependency (cross-version self-dependency). Try not to update its version unless necessary.
    implementation("com.huanshankeji.team:gradle-plugins:0.3.0") { exclude("org.jetbrains.kotlin") }
    // This is also a bootstrapping dependency.
    implementation("com.huanshankeji:common-gradle-dependencies:0.5.0-20230310") { exclude("org.jetbrains.kotlin") }
}
