plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
    id("com.huanshankeji.kotlin-abi-validation-conventions")
}

dependencies {
    implementation(kotlin("gradle-plugin"))

    implementation(project(":common-gradle-dependencies"))
}

kotlin {
    compilerOptions {
        optIn.addAll(
            "com.huanshankeji.InternalApi",
            "org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation",
        )
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
