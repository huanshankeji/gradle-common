package com.huanshankeji.gitversioning

/** Semver release, optionally with one of alpha/beta/rc and an extra number, e.g. `1.2.3`, `1.2.3-alpha`, `1.2.3-alpha-1`. */
const val RELEASE_VERSION_REGEX =
    """\d+\.\d+\.\d+(-(alpha|beta|rc)(-\d+)?)?"""

/** Clean committed dev build, e.g. `1.0.0-dev-commit-abc123`. */
const val DEV_COMMIT_VERSION_REGEX =
    """$RELEASE_VERSION_REGEX-dev-commit-[0-9a-f]+$"""

/** Snapshot versions (dirty and legacy), e.g. `1.0.0-dev-commit-abc123-dirty-SNAPSHOT`. */
const val SNAPSHOT_VERSION_REGEX = ".*-SNAPSHOT$"
