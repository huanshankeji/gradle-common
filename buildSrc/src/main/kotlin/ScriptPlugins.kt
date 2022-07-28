import org.gradle.api.NamedDomainObjectContainer
import org.gradle.plugin.devel.PluginDeclaration

fun NamedDomainObjectContainer<PluginDeclaration>.scriptPlugin(
    `package`: String, shortName: String, displayName: String, description: String = displayName
) =
    getByName("$`package`.$shortName") {
        this.displayName = displayName
        this.description = description
    }
