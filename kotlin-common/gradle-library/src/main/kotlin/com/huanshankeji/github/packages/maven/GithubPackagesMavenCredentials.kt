package com.huanshankeji.github.packages.maven

import org.gradle.api.provider.ProviderFactory

fun ProviderFactory.githubPackagesMavenUsername(): String? =
    gradleProperty("gpr.user").orElse(gradleProperty("gprUser")).getOrNull()

fun ProviderFactory.githubPackagesMavenPassword(): String? =
    gradleProperty("gpr.key").orElse(gradleProperty("gprKey")).getOrNull()
