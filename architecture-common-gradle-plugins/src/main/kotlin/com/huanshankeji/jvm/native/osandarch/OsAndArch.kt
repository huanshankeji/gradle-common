package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.jvm.native.osandarch.CpuArchitecture.Aarch64
import com.huanshankeji.jvm.native.osandarch.CpuArchitecture.X8664
import com.huanshankeji.jvm.native.osandarch.Os.*
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import java.util.*

// TODO possibly use feature variant name
enum class Os(val identifier: String) {
    Linux("linux"), Windows("windows"), Macos("osx");

    // Note: not using `get()` in an enum here causes initialization errors.
    val featureVariantName: String get() = identifier.toFeatureVariantName()
}

enum class CpuArchitecture(val identifier: String) {
    X8664("x86_64"), Aarch64("aarch64");

    val featureVariantName: String get() = identifier.toFeatureVariantName()
}


val supportedOsArchs = listOf(
    Linux to listOf(X8664, Aarch64),
    Windows to listOf(X8664),
    Macos to listOf(X8664, Aarch64)
)
val supportedOsArchMap = EnumMap(supportedOsArchs.toMap())

data class OsAndArch(val os: Os, val arch: CpuArchitecture)

fun getCurrentOsAndArch(): OsAndArch {
    val currentOperatingSystem = DefaultNativePlatform.getCurrentOperatingSystem()
    val os = when {
        currentOperatingSystem.isLinux -> Linux
        currentOperatingSystem.isWindows -> Windows
        currentOperatingSystem.isMacOsX -> Macos
        else -> throw IllegalArgumentException("Huanshankeji architecture common unsupported operating system: $currentOperatingSystem")
    }
    val currentArchitecture = DefaultNativePlatform.getCurrentArchitecture()
    val arch = when {
        currentArchitecture.isAmd64 -> X8664
        currentArchitecture.isArm64 -> Aarch64
        else -> throw IllegalArgumentException("Huanshankeji architecture common unsupported CPU architecture: $currentOperatingSystem")
    }
    return OsAndArch(os, arch)
}
