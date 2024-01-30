package com.huanshankeji.benchmark

import com.huanshankeji.SourceSetType
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

interface KotlinxBenchmarkConventionsExtension {
    val sourceSetType: Property<SourceSetType>
}

fun ExtensionContainer.createKotlinxBenchmarkConventionsExtension() =
    create<KotlinxBenchmarkConventionsExtension>("kotlinxBenchmarkConventions")
