import com.huanshankeji.generateKotlinVersion

plugins {
    `build-dependency-library-conventions`
    `java-gradle-plugin` // This plugin is needed to add the necessary Gradle dependencies.
    id("com.huanshankeji.kotlin-jvm-library-sonatype-ossrh-publish-conventions")
}

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
version = "0.3.2-20220728-SNAPSHOT"

generateKotlinVersion(kotlinVersion)

publishing.publications.withType<MavenPublication> {
    pom {
        /*
        name.set("Huanshankeji Gradle Common (in and for Kotlin)")
        description.set("Huanshankeji's Gradle common code in Kotlin, mainly for common projects in Kotlin")
        */
        name.set("Huanshankeji common Gradle dependencies")
        description.set("Huanshankeji's common Gradle dependencies in Kotlin, mainly for common projects in Kotlin")

        url.set(GITHUB_URL)

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("ShreckYe")
                name.set("Shreck Ye")
                email.set("ShreckYe@gmail.com")
            }
        }
        scm {
            connection.set(GITHUB_GIT_URL)
            developerConnection.set(GITHUB_GIT_URL)
            url.set(GITHUB_URL)
        }
    }
}
