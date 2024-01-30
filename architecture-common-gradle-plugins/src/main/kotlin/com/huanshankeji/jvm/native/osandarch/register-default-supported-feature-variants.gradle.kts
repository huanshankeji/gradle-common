package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.SourceSetType

plugins {
    java
}

interface Extension {
    val sourceSetType: Property<SourceSetType>
}

// TODO put in `afterEvaluate`?

val extension = extensions.create<Extension>("registerOsAndArchFeatureVariants")

java.registerDefaultSupportedFeatureVariants(extension.sourceSetType.getOrElse(SourceSetType.Main))
