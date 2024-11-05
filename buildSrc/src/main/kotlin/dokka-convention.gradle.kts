plugins {
    id("org.jetbrains.dokka")
}

dokka {
    dokkaSourceSets.all {
        sourceLink {
            remoteUrl("https://github.com/huanshankeji/kotlin-common/tree/v${version}/${project.name}")
            remoteLineSuffix.set("#L")
        }
    }
}
