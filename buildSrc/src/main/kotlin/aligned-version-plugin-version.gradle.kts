import com.huanshankeji.gitversioning.projectVersionFromGitProvider

// TODO don't use `afterEvaluate`
afterEvaluate {
    version = projectVersionFromGitProvider(alignedPluginBaseVersion).get()
}
