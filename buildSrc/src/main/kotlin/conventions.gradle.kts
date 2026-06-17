plugins {
    id("org.gradle.kotlin.kotlin-dsl")
    id("com.huanshankeji.team.with-group")

    id("com.gradle.plugin-publish")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
}

import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.credentials

// Bootstrap 0.11.0 still configures publishing credentials eagerly; override with lazy providers
// until buildSrc depends on a released gradle-plugins version from this repo.
afterEvaluate {
    extensions.configure<PublishingExtension>("publishing") {
        repositories {
            matching { it.name == "GitHubPackages" }.configureEach {
                credentials(PasswordCredentials::class) {
                    username.set(
                        providers.gradleProperty("gpr.user")
                            .orElse(providers.gradleProperty("gprUser"))
                            .orElse(providers.environmentVariable("GPR_USER"))
                            .orElse(providers.environmentVariable("GITHUB_ACTOR")),
                    )
                    password.set(
                        providers.gradleProperty("gpr.key")
                            .orElse(providers.gradleProperty("gprKey"))
                            .orElse(providers.environmentVariable("GPR_KEY"))
                            .orElse(providers.environmentVariable("GITHUB_TOKEN")),
                    )
                }
            }
        }
    }
}

repositories {
    mavenLocal() // comment out when not needed
    gradlePluginPortal()
}

dependencies {
    // Not specifying version can cause build issues when added to a project's buildscript dependencies if the version in the "buildSrc" build script is different.
    implementation(kotlin("gradle-plugin"))
    // These 2 dependencies are implicitly added with the Gradle's embedded Kotlin version if not added explicitly.
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}

kotlin.jvmToolchain(17)


gradlePlugin {
    website.set(GITHUB_URL)
    vcsUrl.set(GITHUB_GIT_URL)
    plugins.all { tags.set(listOf("kotlin", "kotlin-multiplatform")) }
}
