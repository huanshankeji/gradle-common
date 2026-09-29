package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.SourceSetType

plugins {
    java
}

interface Extension {
    val sourceSetType: Property<SourceSetType>
}

val extension = extensions.create<Extension>("registerOsAndArchFeatureVariants")

/*
Registers immediately. Do not wrap this in `afterEvaluate` — see
https://docs.gradle.org/current/userguide/best_practices_general.html#avoid_after_evaluate.
The [Extension.sourceSetType] property is not read after the consuming build script runs;
this plugin always uses [SourceSetType.Main] when applied via `plugins {}`.
For [SourceSetType.RegisterSeparate], call
[JavaPluginExtension.registerDefaultSupportedFeatureVariants] instead of this property.
 */
java.registerDefaultSupportedFeatureVariants(extension.sourceSetType.getOrElse(SourceSetType.Main))
