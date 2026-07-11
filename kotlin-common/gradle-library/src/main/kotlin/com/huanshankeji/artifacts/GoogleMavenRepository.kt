package com.huanshankeji.artifacts

import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Copied from a KMP template.
 */
fun RepositoryHandler.googleWithContentFiltering() =
   google {
        mavenContent {
            includeGroupAndSubgroups("androidx")
            includeGroupAndSubgroups("com.android")
            includeGroupAndSubgroups("com.google")
        }
    }
