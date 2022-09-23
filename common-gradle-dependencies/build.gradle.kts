import com.huanshankeji.generateKotlinVersion
import com.huanshankeji.team.`Shreck Ye`
import com.huanshankeji.team.pomForTeamDefaultOpenSource

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
    pomForTeamDefaultOpenSource(
        project,
        "Huanshankeji common Gradle dependencies",
        "Huanshankeji's common Gradle dependencies in Kotlin, mainly for common projects in Kotlin"
    ) {
        `Shreck Ye`()
    }
}
