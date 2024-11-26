plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
}

dependencies {
    implementation("com.huanshankeji:common-gradle-dependencies:$pluginProjectSourceDependentStableCommonGradleDependenciesVersion")
}

/*
tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.InternalApi")
}
*/
