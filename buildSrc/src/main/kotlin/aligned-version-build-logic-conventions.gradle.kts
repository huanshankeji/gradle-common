import com.huanshankeji.gitversioning.devCommitVersionProvider
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("common-conventions")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

// On the release branch, set `version = alignedPluginBaseVersion` explicitly.
version = providers.devCommitVersionProvider(alignedPluginBaseVersion).get()

kotlin {
    compilerOptions {
        optIn.addAll(
            "com.huanshankeji.GradleCommonInternalApi",
            "com.huanshankeji.GradleCommonExperimentalApi",
        )
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    /*
    Though `abiValidation` is enabled for the `huanshankeji-team` modules,
    there is no need to maintain binary compatibility there.
     */
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation()
}
