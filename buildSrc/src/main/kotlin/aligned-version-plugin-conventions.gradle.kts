plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
    id("kotlin-abi-validation")
}

dependencies {
    implementation("com.huanshankeji:common-gradle-dependencies:$pluginProjectSourceDependencyStableCommonGradleDependenciesVersion")
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
