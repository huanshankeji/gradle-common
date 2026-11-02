package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.SourceSetType
import com.huanshankeji.jvm.native.osandarch.registerDefaultSupportedFeatureVariants

plugins {
    java
}

interface Extension {
    val sourceSetType: Property<SourceSetType>
}

// TODO expose source-set selection through an explicit configuration function?

val extension = extensions.create<Extension>("registerOsAndArchFeatureVariants")

java.registerDefaultSupportedFeatureVariants(extension.sourceSetType.getOrElse(SourceSetType.Main))
