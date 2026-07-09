import com.huanshankeji.gitversioning.projectVersionFromGitProvider

plugins {
    id("conventions")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

version = projectVersionFromGitProvider(alignedPluginBaseVersion).get()
