package com.huanshankeji

import org.gradle.api.provider.Provider

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral()
}

fun wireMavenCentralSigning(versionProvider: Provider<String>) {
    version = versionProvider.map { version ->
        if (isMavenCentralSigningVersion(version)) mavenPublishing.signAllPublications()
        version
    }.asLazyProjectVersion()
}

pluginManager.withPlugin("com.huanshankeji.git-version") {
    val gitVersion = extensions.getByName("gitVersion") as GitVersionExtension
    wireMavenCentralSigning(projectVersionFromGitProvider(gitVersion.baseVersion))
}
if (!plugins.hasPlugin("com.huanshankeji.git-version")) {
    wireMavenCentralSigning(providers.provider { version.toString() })
}

// should probably require the Java toolchain version to be set too when using this plugin
