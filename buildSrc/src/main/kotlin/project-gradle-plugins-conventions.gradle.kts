plugins {
    id("aligned-version-build-logic-conventions")
}

dependencies {
    // Put in `gradle-library-conventions.gradle.kts` if more code is moved to the `gradle-library` modules in the future.
    implementation(kotlin("gradle-plugin"))

    implementation(project(":common-gradle-dependencies"))
}
