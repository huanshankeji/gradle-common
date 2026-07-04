plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

dependencies {
    implementation(kotlin("gradle-plugin"))

    implementation(project(":common-gradle-dependencies"))
}

kotlin {
    compilerOptions {
        optIn.addAll(
            "com.huanshankeji.GradleCommonInternalApi",
            "com.huanshankeji.GradleCommonExperimentalApi",
        )
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
