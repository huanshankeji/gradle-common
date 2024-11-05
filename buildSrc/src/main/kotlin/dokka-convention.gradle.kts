plugins {
    id("org.jetbrains.dokka")
}

dokka {
    dokkaSourceSets.all {
        sourceLink {
            remoteUrl("https://github.com/huanshankeji/gradle-common/tree/v${version}/${project.name}")
            remoteLineSuffix.set("#L")
        }
    }
}
