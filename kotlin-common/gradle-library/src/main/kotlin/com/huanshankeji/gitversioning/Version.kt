package com.huanshankeji.gitversioning

import com.huanshankeji.RELEASE_VERSION_REGEX
import com.huanshankeji.SNAPSHOT_VERSION_REGEX

/** Clean committed dev build, e.g. `1.0.0-dev-commit-abc123`. */
const val DEV_COMMIT_VERSION_REGEX = """$RELEASE_VERSION_REGEX-dev-commit-[0-9a-f]+"""

/** Matches [SNAPSHOT_VERSION_REGEX] or [DEV_COMMIT_VERSION_REGEX]. */
const val SNAPSHOT_AND_DEV_COMMIT_VERSION_REGEX = "$SNAPSHOT_VERSION_REGEX|$DEV_COMMIT_VERSION_REGEX"

/** Matches [DEV_COMMIT_VERSION_REGEX] or [RELEASE_VERSION_REGEX]. */
const val DEV_COMMIT_AND_RELEASE_VERSION_REGEX = "$DEV_COMMIT_VERSION_REGEX|$RELEASE_VERSION_REGEX"
