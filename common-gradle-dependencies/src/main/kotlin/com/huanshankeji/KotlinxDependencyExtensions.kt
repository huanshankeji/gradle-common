package com.huanshankeji

/**
 * @see org.gradle.kotlin.dsl.kotlin
 */
fun kotlinx(module: String, version: String? = null): String =
    "org.jetbrains.kotlinx:kotlinx-$module" + (version?.let { ":$it" } ?: "")
