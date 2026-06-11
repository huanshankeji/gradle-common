import com.huanshankeji.team.maven.configureMavenCentralExcludeHuanshankejiNonStable
import com.huanshankeji.team.maven.configurePublicHuanshankejiArtifactRepositories
import org.gradle.api.provider.ListProperty

interface PublicOpenSourceDependencyRepositoriesExtension {
    val githubPackageRepositoryNames: ListProperty<String>
}

val extension = extensions.create<PublicOpenSourceDependencyRepositoriesExtension>(
    "publicOpenSourceDependencyRepositories"
)
extension.githubPackageRepositoryNames.convention(listOf(rootProject.name))

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        configurePublicHuanshankejiArtifactRepositories(
            settings,
            githubPackageRepositoryNames = extension.githubPackageRepositoryNames.get(),
        )
        mavenCentral {
            configureMavenCentralExcludeHuanshankejiNonStable()
        }
    }
}
