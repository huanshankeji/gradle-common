import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("conventions")
    id("dokka-convention")
}

version = alignedPluginVersion

dependencies {
    implementation("com.huanshankeji:common-gradle-dependencies:$pluginProjectSourceDependentStableCommonGradleDependenciesVersion")
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.InternalApi")
}
