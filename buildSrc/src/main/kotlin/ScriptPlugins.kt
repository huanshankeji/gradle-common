import org.gradle.api.NamedDomainObjectContainer
import org.gradle.plugin.devel.PluginDeclaration

fun NamedDomainObjectContainer<PluginDeclaration>.scriptPlugin(
    `package`: String, idSuffix: String, displayName: String, description: String = displayName
) =
    getByName("$`package`.$idSuffix") {
        this.displayName = displayName
        this.description = description
    }
