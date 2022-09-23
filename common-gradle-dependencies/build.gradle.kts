import com.huanshankeji.generateKotlinVersion
import com.huanshankeji.team.`Shreck Ye`
import com.huanshankeji.team.pomForTeamDefaultOpenSource

plugins {
    `common-gradle-dependencies-helper`

    // This project is published to Maven Central instead of the Gradle Plugin Portal.

    `java-gradle-plugin` // This plugin is needed to add the necessary Gradle dependencies.
    id("com.huanshankeji.sonatype-ossrh-publish")
}

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
