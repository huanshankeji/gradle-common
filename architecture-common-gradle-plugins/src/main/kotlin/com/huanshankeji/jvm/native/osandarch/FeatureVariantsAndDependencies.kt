package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.*
import com.huanshankeji.SourceSetType.Main
import com.huanshankeji.SourceSetType.RegisterSeparate
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.accessors.runtime.addExternalModuleDependencyTo
import org.gradle.kotlin.dsl.get

val OsAndArch.featureVariantName get() = camelCaseIdentifier


fun JavaPluginExtension.registerDefaultSupportedFeatureVariants(sourceSetType: SourceSetType) {
    when (sourceSetType) {
        Main -> {
            val mainSourceSet = sourceSets["main"]
            for ((index, osAndArch) in DefaultSupported.OsAndArchs.all.withIndex()) {
                // Only add javadoc and sources jars for the first feature variant to avoid creating duplicate configurations
                val withJavadocAndSourcesJars = index == 0
                registerFeatureVariantWithSourceSet(osAndArch.featureVariantName, mainSourceSet, withJavadocAndSourcesJars)
            }
        }

        RegisterSeparate ->
            for (osAndArch in DefaultSupported.OsAndArchs.all)
                registerFeatureVariantWithNewSourceSet(osAndArch.featureVariantName)
    }
}


/**
 * @param identifier usually in kebab case (sometimes mixed with snake case, for example "X86_64")
 */
data class FeatureVariantDependencyConfig(val osAndArch: OsAndArch, val identifier: String)

fun DependencyHandlerScope.addDependencyToFeatureVariants(
    osAndArchs: List<OsAndArch>, targetConfigurationType: String, dependencyNotation: Any
) =
    addDependencyToFeatureVariants(
        osAndArchs.map { it.featureVariantName }, targetConfigurationType, dependencyNotation
    )


// TODO some functions related to feature variants can be extracted to a separate feature variant package

/**
 * @param osAndArchs use a predefined one in [DefaultSupported.OsAndArchs] or make your own with [FeatureVariantDependencyConfig]
 * @param targetConfigurationType the type of the dependency configuration
 * (see https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:what-are-dependency-configurations
 * and https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph)
 */
fun DependencyHandlerScope.addDependenciesToFeatureVariantsWithIdentifiersInNameSuffixes(
    osAndArchs: List<FeatureVariantDependencyConfig>,
    targetConfigurationType: String,
    group: String, namePrefix: String, version: String? = null
) {
    for ((osAndArch, dependencyIdentifier) in osAndArchs)
        addExternalModuleDependencyTo(
            this,
            osAndArch.featureVariantName camelCaseConcat targetConfigurationType,
            group, "$namePrefix-$dependencyIdentifier", version,
            null, null, null, null
        )
}

/** @see addDependenciesToFeatureVariantsWithIdentifiersInNameSuffixes */
fun DependencyHandlerScope.addDependenciesToFeatureVariantsWithIdentifiersInNameSuffixes(
    osAndArchs: List<OsAndArch>, getIdentifier: (OsAndArch) -> String,
    targetConfigurationType: String,
    group: String, namePrefix: String, version: String? = null
) =
    addDependenciesToFeatureVariantsWithIdentifiersInNameSuffixes(osAndArchs.map {
        FeatureVariantDependencyConfig(it, getIdentifier(it))
    }, targetConfigurationType, group, namePrefix, version)

/**
 * @param configs use a predefined one in [DefaultSupported.OsAndArchs] or make your own with [FeatureVariantDependencyConfig]
 * @param targetConfigurationType the type of the dependency configuration
 * (see https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:what-are-dependency-configurations
 * and https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph)
 */
fun DependencyHandlerScope.addDependenciesToFeatureVariantsWithIdentifiersInClassifiers(
    configs: List<FeatureVariantDependencyConfig>,
    targetConfigurationType: String,
    group: String, name: String, version: String? = null
) {
    for ((osAndArch, dependencyIdentifier) in configs)
        addExternalModuleDependencyTo(
            this,
            osAndArch.featureVariantName camelCaseConcat targetConfigurationType,
            group, name, version,
            null, dependencyIdentifier, null, null
        )
}

/** @see addDependenciesToFeatureVariantsWithIdentifiersInClassifiers */
fun DependencyHandlerScope.addDependenciesToFeatureVariantsWithIdentifiersInClassifiers(
    osAndArchs: List<OsAndArch>, getIdentifier: (OsAndArch) -> String,
    targetConfigurationType: String,
    group: String, name: String, version: String? = null
) =
    addDependenciesToFeatureVariantsWithIdentifiersInClassifiers(osAndArchs.map {
        FeatureVariantDependencyConfig(it, getIdentifier(it))
    }, targetConfigurationType, group, name, version)


fun getCapabilityNotation(group: String, name: String, featureVariantName: String) =
    "$group:$name-${featureVariantName.camelCaseToKebabCase()}"

private inline fun DependencyHandlerScope.addDependencyWithFeatureVariantCapabilityDependencies(
    featureVariantNames: List<String>, targetConfiguration: (featureVariantName: String?) -> String,
    group: String, name: String, version: String? = null
) {
    addExternalModuleDependencyTo(this, targetConfiguration(null), group, name, version, null, null, null, null)
    for (featureVariantName in featureVariantNames)
        addExternalModuleDependencyTo(
            this,
            targetConfiguration(featureVariantName),
            group, name, version,
            null, null, null
        ) {
            capabilities {
                requireCapability(getCapabilityNotation(group, name, featureVariantName))
            }
        }
}

fun DependencyHandlerScope.addDependencyWithFeatureVariantTransitiveCapabilityDependencies(
    featureVariantNames: List<String>, targetConfigurationType: String,
    group: String, name: String, version: String? = null
) =
    addDependencyWithFeatureVariantCapabilityDependencies(
        featureVariantNames,
        { featureVariantName ->
            featureVariantName?.camelCaseConcat(targetConfigurationType) ?: targetConfigurationType
        },
        group, name, version
    )

/**
 * Adds all the feature variant dependencies to the main variant.
 * Use this function without the `register-feature-variants` plugin.
 */
fun DependencyHandlerScope.addDependencyWithFeatureVariantCapabilityDependenciesToOneConfiguration(
    featureVariantNames: List<String>, targetConfiguration: String,
    group: String, name: String, version: String? = null
) =
    addDependencyWithFeatureVariantCapabilityDependencies(
        featureVariantNames,
        { _ -> targetConfiguration },
        group, name, version
    )
