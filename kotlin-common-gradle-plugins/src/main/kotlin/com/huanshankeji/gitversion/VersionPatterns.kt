package com.huanshankeji.gitversion

/** Clean committed dev build, e.g. `1.0.0-dev-commit-abc123`. */
const val DEV_COMMIT_VERSION_REGEX = ".*-dev-commit-[0-9a-f]+$"

/** Uncommitted dirty build, e.g. `1.0.0-dev-commit-abc123-dirty-SNAPSHOT`. */
const val DIRTY_DEV_COMMIT_VERSION_REGEX = ".*-dev-commit-[0-9a-f]+-dirty-SNAPSHOT$"

/** Legacy snapshot versions. */
const val LEGACY_SNAPSHOT_VERSION_REGEX = ".*-SNAPSHOT$"
