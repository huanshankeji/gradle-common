package com.huanshankeji.jvm.native.osandarch

import com.huanshankeji.jvm.native.osandarch.Os.*
import com.huanshankeji.jvm.native.osandarch.SourceSetType.Main
import com.huanshankeji.jvm.native.osandarch.SourceSetType.RegisterSeparate
import com.huanshankeji.registerFeatureVariantWithNewSourceSet
import com.huanshankeji.registerFeatureVariantWithSourceSet
import gradle.kotlin.dsl.accessors._4d764c3096d994e4212b8a01328470fd.runtimeOnly
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.accessors.runtime.addExternalModuleDependencyTo
import org.gradle.kotlin.dsl.get

val featureVariantNameCharRegex = Regex("[a-zA-Z0-9]")
val featureVariantNameRegex = Regex("${featureVariantNameCharRegex.pattern}+")
fun String.toFeatureVariantName() =
    featureVariantNameCharRegex.findAll(this).map { it.value }.joinToString("")

fun String.isValidFeatureVariantName() =
    matches(featureVariantNameRegex)

fun String.camelCaseToKebabCase() =
    replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }


fun String.capitalizeFirstChar() =
    replaceFirstChar { it.uppercaseChar() }


infix fun String.camelCaseConcat(other: String) =
    this + other.capitalizeFirstChar()


fun OsAndArch.getFeatureVariantName() =
    os.featureVariantName camelCaseConcat arch.featureVariantName


/**
 * @param featureVariantName usually in camel case
 * @param dependencyIdentifier usually in kebab case (sometimes mixed with snake case, for example "X86_64")
 */
data class Config(val featureVariantName: String, val dependencyIdentifier: String) // TODO classifier

fun Pair<Os, List<CpuArchitecture>>.toConfigSequence(): Sequence<Config> {
    val (os, archs) = this
    return archs.asSequence().map { arch ->
        Config(os.featureVariantName camelCaseConcat arch.featureVariantName, "${os.identifier}-${arch.identifier}")
    }
}

fun Pair<Os, List<CpuArchitecture>>.toConfigs() =
    toConfigSequence().toList()

object Configs {
    /*
    // TODO use `entries` when the language version is bumped to 1.9
    val ofAllOss = Os.values().map {
        Config(it.featureVariantName, it.identifier)
    }
    */

    private fun Os.getConfigs() =
        (this to supportedOsArchMap.getValue(this)).toConfigs()

    val linux = Linux.getConfigs()
    val windows = Windows.getConfigs()
    val macos = Macos.getConfigs()

    val all = supportedOsArchs.flatMap { it.toConfigSequence() }
}

enum class SourceSetType {
    Main, RegisterSeparate
}

fun JavaPluginExtension.registerFeatureVariants(sourceSetType: SourceSetType) {
    when (sourceSetType) {
        Main -> {
            val mainSourceSet = sourceSets["main"]
            for (config in Configs.all)
                registerFeatureVariantWithSourceSet(config.featureVariantName, mainSourceSet)
        }

        RegisterSeparate ->
            for (config in Configs.all)
                registerFeatureVariantWithNewSourceSet(config.featureVariantName)
    }
}

fun DependencyHandlerScope.addAllFeatureVariantDependencies(
    featureVariantNames: List<String>, targetConfigurationType: String, dependencyNotation: Any
) {
    for (featureVariantName in featureVariantNames)
        add(featureVariantName camelCaseConcat targetConfigurationType, dependencyNotation)
}


// TODO some functions related to feature variants can be extracted to a separate feature variant package

/**
 * @param configs use a predefined one in [Configs] or make your own with [Config]
 * @param targetConfigurationType the type of the dependency configuration
 * (see https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:what-are-dependency-configurations
 * and https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph)
 */
fun DependencyHandlerScope.addAllFeatureVariantDependenciesWithIdentifiersInNameSuffixes(
    configs: List<Config>,
    targetConfigurationType: String,
    group: String,
    namePrefix: String,
    version: String? = null
) {
    for ((featureVariantName, dependencyIdentifier) in configs)
        addExternalModuleDependencyTo(
            this,
            featureVariantName + targetConfigurationType.capitalizeFirstChar(),
            group, "$namePrefix-$dependencyIdentifier", version,
            null, null, null, null
        )
}

/**
 * @param configs use a predefined one in [Configs] or make your own with [Config]
 * @param targetConfigurationType the type of the dependency configuration
 * (see https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:what-are-dependency-configurations
 * and https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph)
 */
fun DependencyHandlerScope.addAllFeatureVariantDependenciesWithIdentifiersInClassifiers(
    configs: List<Config>,
    targetConfigurationType: String,
    group: String,
    name: String,
    version: String? = null
) {
    for ((featureVariantName, dependencyIdentifier) in configs)
        addExternalModuleDependencyTo(
            this,
            featureVariantName + targetConfigurationType.replaceFirstChar { it.uppercaseChar() },
            group, name, version,
            null, dependencyIdentifier, null, null
        )
}


fun getCapabilityNotation(group: String, name: String, featureVariantName: String) =
    "$group:$name-${featureVariantName.camelCaseToKebabCase()}"

inline fun DependencyHandlerScope.addDependencyWithFeatureVariantCapabilityDependencies(
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

// TODO remove
/*
fun DependencyHandlerScope.addFeatureVariantTransitiveCapabilityDependencies(
    featureVariantNames: List<String>, targetConfigurationType: String,
    group: String, name: String, version: String? = null
) {
    for (featureVariantName in featureVariantNames)
        addExternalModuleDependencyTo(
            this,
            featureVariantName camelCaseConcat targetConfigurationType,
            group, name, version,
            null, null, null
        ) {
            capabilities {
                requireCapability(getCapabilityNotation(group, name, featureVariantName))
            }
        }
}
*/

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

// TODO remove. This function is not necessary as all feature variant dependencies are added when running code and packaging distributions.
fun DependencyHandlerScope.addConventionalDependencyWithOsAndArch(
    osAndArchForMain: OsAndArch = getCurrentOsAndArch(), sourceSets: SourceSetContainer,
    featureVariantNames: List<String>, targetConfigurationType: String,
    group: String, name: String, version: String? = null
) {
    addDependencyWithFeatureVariantTransitiveCapabilityDependencies(
        featureVariantNames, targetConfigurationType, group, name, version
    )
    runtimeOnly(sourceSets[osAndArchForMain.getFeatureVariantName()])
}
