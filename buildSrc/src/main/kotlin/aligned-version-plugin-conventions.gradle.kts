plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
}

dependencies {
    implementation(kotlin("gradle-plugin"))

    implementation(project(":common-gradle-dependencies"))
}

kotlin {
    compilerOptions {
        optIn.add("com.huanshankeji.InternalApi")
    }
}
