// extracted into a separate script so the version can be set before `dokka-convention`

val base = alignedPluginBaseVersion
val branch = providers.exec {
    commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
}.standardOutput.asText.get().trim()
val hash = providers.exec {
    commandLine("git", "rev-parse", "HEAD")
}.standardOutput.asText.get().trim()
val dirty = providers.exec {
    commandLine("git", "status", "--porcelain")
}.standardOutput.asText.get().isNotBlank()
version = when {
    branch == "release" -> base
    dirty -> "$base-dev-commit-$hash-dirty-SNAPSHOT"
    else -> "$base-dev-commit-$hash"
}
