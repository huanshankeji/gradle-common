plugins {
    id("conventions")
    id("aligned-version-plugin-version")
    id("dokka-convention")
}

dependencies {
    implementation(kotlin("gradle-plugin"))

    // Depend on the local project directly (developed and released together) instead of a
    // stale released version, removing the cross-version self-dependency (#54).
    implementation(project(":common-gradle-dependencies"))
}

kotlin {
    compilerOptions {
        optIn.add("com.huanshankeji.InternalApi")
    }
}
