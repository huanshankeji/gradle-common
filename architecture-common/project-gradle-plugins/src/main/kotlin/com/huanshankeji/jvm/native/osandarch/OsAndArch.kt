package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.camelCaseConcat
import com.huanshankeji.jvm.native.osandarch.CpuArchitecture.Aarch64
import com.huanshankeji.jvm.native.osandarch.CpuArchitecture.X8664
import com.huanshankeji.jvm.native.osandarch.Os.*
import com.huanshankeji.kebabCaseConcat
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

// TODO possibly use feature variant name
enum class Os(val identifier: String) {
    Linux("linux"), Windows("windows"), Macos("osx")
}

enum class CpuArchitecture(val identifier: String) {
    X8664("x8664"), Aarch64("aarch64")
}

data class OsAndArch(val os: Os, val arch: CpuArchitecture) {
    val kebabCaseIdentifier = os.identifier kebabCaseConcat arch.identifier
    val camelCaseIdentifier = os.identifier camelCaseConcat arch.identifier
}

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
