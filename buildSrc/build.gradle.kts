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
    // for `KotlinCompilationTask` and the version is for Compose 1.6.1 and 1.6.2
    implementation(kotlin("gradle-plugin", "1.9.24"))
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:4.2.1") // This version has to be used for Gradle 8.6.

    implementation("com.gradle.publish:plugin-publish-plugin:1.2.1")

    // This is a bootstrapping dependency (cross-version self-dependency). Try not to update its version unless necessary.
    implementation("com.huanshankeji.team:gradle-plugins:0.3.0") { exclude("org.jetbrains.kotlin") }
    // This is also a bootstrapping dependency.
    implementation("com.huanshankeji:common-gradle-dependencies:0.7.1-20240314-boostrap") { exclude("org.jetbrains.kotlin") }
}
