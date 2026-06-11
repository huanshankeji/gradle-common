import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
    id("kotlin-abi-validation")
}

dependencies {
    implementation("com.huanshankeji:common-gradle-dependencies:$pluginProjectSourceDependencyStableCommonGradleDependenciesVersion")
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_4)
        freeCompilerArgs.addAll(
            "-opt-in=com.huanshankeji.InternalApi",
            "-opt-in=org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation",
            "-Xcontext-parameters",
        )
    }
}
