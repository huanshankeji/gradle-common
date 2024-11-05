plugins {
    id("org.jetbrains.dokka")
}

dokka {
    dokkaSourceSets.all {
        sourceLink {
            remoteUrl("https://github.com/huanshankeji/gradle-common/tree/894a09f38eb5cabc017230bb580fd1b19da4780d/${project.name}")
            remoteLineSuffix.set("#L")
        }
    }
}
