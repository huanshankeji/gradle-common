package com.huanshankeji.team

import com.huanshankeji.pomForDefaultOpenSourceWithApacheLicense20OnGitHub
import com.huanshankeji.team.github.defaultRootProjectGithubRepositoryUrl
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication

// TODO: use context receivers when it's stable
fun MavenPublication.pomForTeamDefaultOpenSource(
    project: Project,
    nameArg: String, descriptionArg: String,
    gitProjectPageUrl: String = project.defaultRootProjectGithubRepositoryUrl(),
    gitUrl: String = "$gitProjectPageUrl.git", scmConnection: String = "scm:git:$gitUrl",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) =
    pomForDefaultOpenSourceWithApacheLicense20OnGitHub(
        nameArg, descriptionArg, gitProjectPageUrl, gitUrl, scmConnection, developersBlock
    )

fun MavenPomDeveloperSpec.`Shreck Ye`() =
    developer {
        id.set("ShreckYe")
        name.set("Shreck Ye")
        email.set("ShreckYe@gmail.com")
    }
