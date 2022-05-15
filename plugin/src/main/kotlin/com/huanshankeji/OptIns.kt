package com.huanshankeji

import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder

fun LanguageSettingsBuilder.defaultOptIns() {
    optIn("kotlin.RequiresOptIn")
    optIn("kotlin.ExperimentalUnsignedTypes")
    optIn("kotlinx.serialization.ExperimentalSerializationApi")
}
