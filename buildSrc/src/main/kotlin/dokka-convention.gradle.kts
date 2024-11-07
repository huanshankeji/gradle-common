plugins {
    id("org.jetbrains.dokka")
}

dokka {
    dokkaSourceSets.all {
        sourceLink {
            val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl("https://github.com/huanshankeji/gradle-common/blob/plugins-v${version}/$projectRelativePath")
            remoteLineSuffix.set("#L")
        }
    }
}
