This package contains APIs related to our convention for open source projects with Git versioning and mainly Maven repositories now.
APIs publish Git versioning `*-dev-commit-*` versions to a custom Maven repository and release versions to Maven Central,
and consume dirty snapshot version from Maven local,
`*-dev-commit-*` versions from both Maven local and the custom Maven repository,
and release versions from Maven Central.
