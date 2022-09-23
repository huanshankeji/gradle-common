package com.huanshankeji

import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication

// TODO: remove the comment below when this function is used there
// copied and adapted from "/common-gradle-dependencies/build.gradle.kts"
fun MavenPom.setUpForDefaultOpenSourceWithApacheLicense20OnGitHub(
    nameArg: String, descriptionArg: String, githubUrl: String, gitUrl: String = "$githubUrl.git",
    developersBlock: (MavenPomDeveloperSpec) -> Unit
) {
    name.set(nameArg)
    description.set(descriptionArg)

    url.set(githubUrl)

    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    developers(developersBlock)
    scm {
        connection.set(gitUrl)
        developerConnection.set(gitUrl)
        url.set(githubUrl)
    }
}

fun MavenPublication.pomForDefaultOpenSourceWithApacheLicense20OnGitHub(
    nameArg: String, descriptionArg: String, githubUrl: String, gitUrl: String = "$githubUrl.git",
    developersBlock: (MavenPomDeveloperSpec) -> Unit
) =
    pom {
        setUpForDefaultOpenSourceWithApacheLicense20OnGitHub(
            nameArg, descriptionArg, githubUrl, gitUrl, developersBlock
        )
    }
