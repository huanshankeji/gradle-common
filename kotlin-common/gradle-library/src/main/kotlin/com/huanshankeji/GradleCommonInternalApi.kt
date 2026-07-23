package com.huanshankeji

@RequiresOptIn("This API is internal in the `gradle-common` project. The target is not marked with Kotlin's `internal` only because it's accessed across multiple modules.")
@Retention(AnnotationRetention.BINARY)
annotation class GradleCommonInternalApi

@Deprecated("Use `GradleCommonInternalApi` instead.", ReplaceWith("GradleCommonInternalApi"))
annotation class InternalApi
