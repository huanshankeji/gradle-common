package com.huanshankeji

import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication

fun MavenPom.setUpForDefaultOpenSourceWithApacheLicense20OnGitHub(
    nameArg: String, descriptionArg: String,
    gitProjectPageUrl: String,
    gitUrl: String = "$gitProjectPageUrl.git", scmConnection: String = "scm:git:$gitUrl",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) {
    name.set(nameArg)
    description.set(descriptionArg)

    url.set(gitProjectPageUrl)

    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    developers(developersBlock)
    scm {
        connection.set(scmConnection)
        developerConnection.set(scmConnection)
        url.set(gitProjectPageUrl)
    }
}

fun MavenPublication.pomForDefaultOpenSourceWithApacheLicense20OnGitHub(
    nameArg: String, descriptionArg: String,
    gitProjectPageUrl: String,
    gitUrl: String = "$gitProjectPageUrl.git", scmConnection: String = "scm:git:$gitUrl",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) =
    pom {
        setUpForDefaultOpenSourceWithApacheLicense20OnGitHub(
            nameArg, descriptionArg, gitProjectPageUrl, gitUrl, scmConnection, developersBlock
        )
    }
