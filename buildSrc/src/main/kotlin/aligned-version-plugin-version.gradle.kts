// TODO: Replace inlined git-version helpers with a bootstrap dependency on a released
// `com.huanshankeji.git-version` plugin from `kotlin-common-gradle-plugins` in the next release.

import com.huanshankeji.projectVersionFromGitProvider

// TODO Should use the provider direcctly. It seems it's held back by the bootstraping 'dokka-convention' plugin failing with this.
version = projectVersionFromGitProvider(alignedPluginBaseVersion, "plugins-release").get()
