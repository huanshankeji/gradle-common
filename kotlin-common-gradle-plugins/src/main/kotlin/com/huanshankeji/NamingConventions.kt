package com.huanshankeji

val camelCaseCharRegex = Regex("[a-zA-Z0-9]")
val camelCaseRegex = Regex("${camelCaseCharRegex.pattern}+")

infix fun String.kebabCaseConcat(other: String) =
    "$this-$other"

fun String.capitalizeFirstChar() =
    replaceFirstChar { it.uppercaseChar() }

infix fun String.camelCaseConcat(other: String) =
    this + other.capitalizeFirstChar()

fun String.camelCaseToKebabCase() =
    replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }
