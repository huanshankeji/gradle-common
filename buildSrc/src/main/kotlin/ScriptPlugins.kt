import org.gradle.api.NamedDomainObjectContainer
import org.gradle.plugin.devel.PluginDeclaration

fun NamedDomainObjectContainer<PluginDeclaration>.scriptPlugin(
    `package`: String, shortName: String, displayNameAndDescription: String
) =
    getByName("$`package`.$shortName") {
        displayName = displayNameAndDescription
        description = displayNameAndDescription
    }
