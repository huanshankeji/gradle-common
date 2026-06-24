// Register the root build's shared version catalog so `buildSrc` build scripts
// can reference the same dependency versions/coordinates as the root build (#54).
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
