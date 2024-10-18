# "Gradle plugins" change log

## v0.6.0 / 2024-10-18

* remove the bootstrapping "common-gradle-dependencies" dependency, put the shared depdencies in `buildSrc` `DependencyVersions`, and generate versions for "common-gradle-dependencies" (see caf83246808b33bbb854bc486476758c73b9f398 for more details)
* bump Kotlin to 2.0.10 and Compose Multiplatform to 1.7.0
* refactor the `kotlin-multiplatform-jvm-and-js-browser-app-conventions` plugin into `kotlin-multiplatform-app-conventions-with-conventional-targets`
* fix an error in `GenerateKotlinJsBrowserWebrootForVertxWebPlugin` that emerged with the new Kotlin version
* add a `sparkJava17CompatibleJvmArgs` variable
