import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.plugin.devel.PluginDeclaration

fun NamedDomainObjectContainer<PluginDeclaration>.commonScriptPlugin(
    `package`: String, idSuffix: String, displayName: String, description: String = displayName
) =
    getByName("$`package`.$idSuffix") {
        this.displayName = displayName
        this.description = description
    }

// commented out because context recervers can't be used in build scripts yet
/*
context(project: Project)
fun NamedDomainObjectContainer<PluginDeclaration>.scriptPlugin(
    idSuffix: String,
    displayName: String,
    description: String = displayName,
) =
    commonScriptPlugin(project.group as String, idSuffix, displayName, description)
*/
