@file:OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)

package com.huanshankeji

import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins.withId("org.jetbrains.kotlin.jvm") {
    extensions.findByType(KotlinJvmProjectExtension::class.java)?.abiValidation()
}

plugins.withId("org.jetbrains.kotlin.multiplatform") {
    extensions.findByType(KotlinMultiplatformExtension::class.java)?.abiValidation()
}

plugins.withId("org.gradle.kotlin.kotlin-dsl") {
    extensions.findByType(KotlinJvmProjectExtension::class.java)?.abiValidation()
}
