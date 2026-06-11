// TODO: Replace inlined git-version helpers with a bootstrap dependency on a released
// `com.huanshankeji.git-version` plugin from `kotlin-common-gradle-plugins` in the next release.

import com.huanshankeji.projectVersionFromGitProvider

version = projectVersionFromGitProvider(alignedPluginBaseVersion).get()
