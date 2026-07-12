package com.huanshankeji.artifacts

import com.huanshankeji.GradleCommonExperimentalApi
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.ProviderFactory
import java.net.URI

/**
 * Bundles [RepositoryHandler], [ProviderFactory], and a `uri` factory for Maven repository helpers
 * that use context parameters.
 *
 * Prefer this from `settings.gradle.kts`, which cannot enable `-Xcontext-parameters`.
 * When context parameters are available at the call site, prefer calling the context-parameter
 * [RepositoryHandler] extensions directly.
 *
 * When this wrapper is no longer needed, blame and revert the commit that introduced it.
 */
@GradleCommonExperimentalApi
class MavenRepositoryHandlerContext(
    val repositories: RepositoryHandler,
    val providers: ProviderFactory,
    val uri: (path: Any) -> URI,
)

/**
 * Opens a [MavenRepositoryHandlerContext] scope for configuring Maven repositories.
 */
@GradleCommonExperimentalApi
inline fun RepositoryHandler.mavenRepositoryHandlerContext(
    providers: ProviderFactory,
    noinline uri: (path: Any) -> URI,
    block: MavenRepositoryHandlerContext.() -> Unit,
) =
    MavenRepositoryHandlerContext(this, providers, uri).apply(block)
