tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

plugins {
    id("org.jetbrains.dokka")
    id("com.github.ben-manes.versions")
}

evaluationDependsOnChildren()

dependencies {
    /*
    "common-gradle-dependencies" not added because its releases are published in a separate branch, and also because its API documentation is not important
    "huanshankeji-team-gradle-plugins" not added because it's renamed and more cumbersome to configure, and also because it's only used by us
     */
    listOf(
        "kotlin-common-gradle-plugins",
        "architecture-common-gradle-plugins",
    ).forEach {
        dokka(project(":$it"))
    }
}
