package com.huanshankeji

// CPN: concatenated project name path (pure; no Project/Settings dependency)

fun getConcatenatedProjectNamePath(rootProjectName: String, path: String): String {
    val names = path.splitToSequence(':')
    require(names.first() == "")
    return names.drop(1).scan(rootProjectName) { concatenatedName, name ->
        "$concatenatedName-$name"
    }.drop(1).joinToString(":", ":")
}
