import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
}

dependencies {
    implementation("com.huanshankeji:common-gradle-dependencies:$pluginProjectSourceDependencyStableCommonGradleDependenciesVersion")
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.optIn.add("com.huanshankeji.InternalApi")
}
