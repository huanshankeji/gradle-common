import com.huanshankeji.gitversion.projectVersionFromGitProvider

// TODO don't use `afterEvaluate`
afterEvaluate {
    version = projectVersionFromGitProvider(alignedPluginBaseVersion).get()
}
