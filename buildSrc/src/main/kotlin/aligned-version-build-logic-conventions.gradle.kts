import com.huanshankeji.gitversioning.devCommitOrReleaseVersionProvider
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("base-conventions")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

version = providers.devCommitOrReleaseVersionProvider(alignedPluginBaseVersion, isRelease).get()

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
