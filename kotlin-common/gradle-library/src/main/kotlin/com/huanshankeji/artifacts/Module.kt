package com.huanshankeji.artifacts

/**
 * A conventional Maven module-id regex for a project name.
 */
fun leadingProjectNameModuleRegex(projectName: String): String =
    "$projectName.*"
