plugins {
    id("aligned-version-build-logic-conventions")
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
