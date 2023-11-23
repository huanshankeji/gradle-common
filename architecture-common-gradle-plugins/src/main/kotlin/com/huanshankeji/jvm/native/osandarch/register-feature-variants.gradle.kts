package com.huanshankeji.jvm.native.osandarch

plugins {
    java
}

interface Extension {
    val sourceSetType: Property<SourceSetType>
}

val extension = extensions.create<Extension>("registerOsAndArchFeatureVariants")

java.registerFeatureVariants(extension.sourceSetType.getOrElse(SourceSetType.Main))
