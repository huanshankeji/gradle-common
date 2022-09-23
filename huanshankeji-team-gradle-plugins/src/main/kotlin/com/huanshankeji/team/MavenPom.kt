package com.huanshankeji.team

import com.huanshankeji.pomForDefaultOpenSourceWithApacheLicense20OnGitHub
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication

// TODO: use context receivers when it's stable
fun MavenPublication.pomForTeamDefaultOpenSource(
    project: Project,
    nameArg: String,
    descriptionArg: String,
    githubUrl: String = "https://github.com/huanshankeji/${project.rootProject.name}",
    gitUrl: String = "$githubUrl.git",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) =
    pomForDefaultOpenSourceWithApacheLicense20OnGitHub(nameArg, descriptionArg, githubUrl, gitUrl, developersBlock)

fun MavenPomDeveloperSpec.`Shreck Ye`() =
    developer {
        id.set("ShreckYe")
        name.set("Shreck Ye")
        email.set("ShreckYe@gmail.com")
    }
