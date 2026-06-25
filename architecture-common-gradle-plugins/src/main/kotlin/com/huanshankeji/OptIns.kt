package com.huanshankeji

import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions

fun KotlinCommonCompilerOptions.defaultOptIns() {
    optIn.add("kotlin.RequiresOptIn")

    optIn.add("kotlin.ExperimentalStdlibApi")
    optIn.add("kotlin.ExperimentalUnsignedTypes")
    optIn.add("kotlinx.serialization.ExperimentalSerializationApi")
}
