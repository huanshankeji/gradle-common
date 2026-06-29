plugins {
    id("com.huanshankeji.git-version")
}

gitVersion {
    baseVersion.set(alignedPluginBaseVersion)
}
