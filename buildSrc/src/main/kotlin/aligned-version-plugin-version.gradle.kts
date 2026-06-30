plugins {
    id("com.huanshankeji.gitversion.git-version")
}

gitVersion {
    baseVersion.set(alignedPluginBaseVersion)
}
