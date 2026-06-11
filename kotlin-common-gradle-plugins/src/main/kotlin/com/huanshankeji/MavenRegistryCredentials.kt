package com.huanshankeji

fun githubMavenUsername(property: (String) -> String?): String? =
    property("gpr.user") ?: System.getenv("USERNAME") ?: System.getenv("GH_ACTOR")

fun githubMavenPassword(property: (String) -> String?): String? =
    property("gpr.key") ?: System.getenv("TOKEN") ?: System.getenv("GH_TOKEN")

fun gitlabMavenPrivateToken(property: (String) -> String?): String? =
    property("gitLabPrivateToken") ?: System.getenv("GITLAB_PRIVATE_TOKEN")
