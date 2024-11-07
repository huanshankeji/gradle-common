plugins {
    id("org.jetbrains.dokka")
}

// put in `afterEvaluate` so the project version can be retrieved
afterEvaluate {
    dokka {
        dokkaSourceSets.all {
            sourceLink {
                val projectRelativePath = projectDir.relativeTo(rootProject.projectDir)
                remoteUrl("https://github.com/huanshankeji/gradle-common/tree/plugins-v${version}/$projectRelativePath")
                remoteLineSuffix.set("#L")
            }
        }
    }
}
