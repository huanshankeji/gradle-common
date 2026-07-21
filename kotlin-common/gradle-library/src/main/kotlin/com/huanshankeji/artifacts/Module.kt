package com.huanshankeji.artifacts

/**
 * A conventional Maven module-id regex with a leading project name.
 */
fun leadingProjectNameModuleRegex(projectName: String): String =
    """$projectName.*"""
