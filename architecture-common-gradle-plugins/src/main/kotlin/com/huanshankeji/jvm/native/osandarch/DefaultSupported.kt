package com.huanshankeji.jvm.native.osandarch


object DefaultSupported {
    object ArchsByOs {
        val linux = listOf(CpuArchitecture.X8664, CpuArchitecture.Aarch64)
        val windows = listOf(CpuArchitecture.X8664)
        val macos = listOf(CpuArchitecture.X8664, CpuArchitecture.Aarch64)
    }

    object OsAndArchs {
        val linux = ArchsByOs.linux.map { OsAndArch(Os.Linux, it) }
        val windows = ArchsByOs.windows.map { OsAndArch(Os.Windows, it) }
        val macos = ArchsByOs.macos.map { OsAndArch(Os.Macos, it) }

        val all = listOf(linux, windows, macos).flatten()
        val allFeatureVariantNames = all.map { it.featureVariantName }

        // TODO use `entries` when the language version is bumped to 1.9
        val futureAll = Os.values().flatMap { os ->
            CpuArchitecture.values().map { arch ->
                OsAndArch(os, arch)
            }
        }
    }
}