package com.huanshankeji.team

import com.huanshankeji.pomForDefaultOpenSourceWithApacheLicense20OnGitHub
import com.huanshankeji.setUpForDefaultOpenSourceWithApacheLicense20OnGitHub
import com.huanshankeji.team.github.defaultRootProjectGithubRepositoryUrl
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication

fun MavenPom.setUpPomForTeamDefaultOpenSource(
    project: Project,
    name: String, description: String,
    gitProjectPageUrl: String = project.defaultRootProjectGithubRepositoryUrl(),
    gitUrl: String = "$gitProjectPageUrl.git", scmConnection: String = "scm:git:$gitUrl",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) =
    setUpForDefaultOpenSourceWithApacheLicense20OnGitHub(
        name, description, gitProjectPageUrl, gitUrl, scmConnection, developersBlock
    )

// TODO: use context receivers when it's stable
fun MavenPublication.pomForTeamDefaultOpenSource(
    project: Project,
    name: String, description: String,
    gitProjectPageUrl: String = project.defaultRootProjectGithubRepositoryUrl(),
    gitUrl: String = "$gitProjectPageUrl.git", scmConnection: String = "scm:git:$gitUrl",
    developersBlock: MavenPomDeveloperSpec.() -> Unit
) =
    pomForDefaultOpenSourceWithApacheLicense20OnGitHub(
        name, description, gitProjectPageUrl, gitUrl, scmConnection, developersBlock
    )

@Deprecated("The name has been updated.", ReplaceWith("this.ShreckYe()"))
fun MavenPomDeveloperSpec.`Shreck Ye`() =
    developer {
        id.set("ShreckYe")
        name.set("Shreck Ye")
        email.set("ShreckYe@gmail.com")
    }

fun MavenPomDeveloperSpec.ShreckYe() =
    developer {
        id.set("ShreckYe")
        name.set("Yongshun Ye")
        email.set("ShreckYe@gmail.com")
    }
