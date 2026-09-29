package com.huanshankeji.benchmark

import com.huanshankeji.SourceSetType
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.create

abstract class KotlinxBenchmarkConventionsExtension {
    internal lateinit var applySourceSetType: (SourceSetType) -> Unit
    private var sourceSetTypeCalled = false

    /**
     * Applies JVM kotlinx-benchmark source-set conventions immediately.
     * Call at most once after applying
     * `com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions`.
     *
     * Use [SourceSetType.RegisterSeparate] for a `benchmarks` source set that
     * depends on `main` (the previous implicit default), or [SourceSetType.Main]
     * to register the `main` source set as the benchmark target.
     */
    fun sourceSetType(sourceSetType: SourceSetType) {
        check(!sourceSetTypeCalled) {
            "kotlinxBenchmarkConventions.sourceSetType must be called at most once"
        }
        sourceSetTypeCalled = true
        applySourceSetType(sourceSetType)
    }

    internal fun ensureSourceSetTypeCalled() {
        check(sourceSetTypeCalled) {
            "kotlinxBenchmarkConventions.sourceSetType(...) must be called when using " +
                    "com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions"
        }
    }
}

fun ExtensionContainer.createKotlinxBenchmarkConventionsExtension() =
    create<KotlinxBenchmarkConventionsExtension>("kotlinxBenchmarkConventions")
