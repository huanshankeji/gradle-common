plugins {
    kotlin("jvm")
}

kotlin.jvmToolchain(17)

dependencies {
    compileOnly(gradleApi())
    compileOnly(kotlin("gradle-plugin"))
}
