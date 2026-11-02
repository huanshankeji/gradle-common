package com.huanshankeji

import org.gradle.testkit.runner.GradleRunner
import java.nio.file.Files
import kotlin.test.Test

class ConfigurationLifecycleTest {
    private fun checkBuild(script: String) {
        val directory = Files.createTempDirectory("configuration-lifecycle").toFile()
        try {
            directory.resolve("settings.gradle").writeText("rootProject.name = 'lifecycle-test'")
            directory.resolve("build.gradle").writeText(script.trimIndent())
            GradleRunner.create().withProjectDir(directory).withPluginClasspath()
                .withArguments("help", "--stacktrace", "--offline", "--max-workers=2").build()
        } finally {
            directory.deleteRecursively()
        }
    }

    @Test
    fun `deprecated publishing plugins defer repository values and preserve names`() = checkBuild(
        """
        plugins {
            id 'java-library'
            id 'com.huanshankeji.github-packages-maven-publish'
            id 'com.huanshankeji.gitlab-project-level-maven-endpoint-publish'
        }
        assert publishing.repositories.names.containsAll(['GitHubPackages', 'GitLab'])
        githubPackagesPublish.owner.set('example')
        githubPackagesPublish.repository.set('library')
        gitlabPackagesPublish.projectId.set('123')
        assert publishing.repositories.GitHubPackages.url.toString() == 'https://maven.pkg.github.com/example/library'
        assert publishing.repositories.GitLab.url.toString() == 'https://gitlab.com/api/v4/projects/123/packages/maven'
        gitlabPackagesPublish.host.set('gitlab.example.com')
        assert publishing.repositories.GitLab.url.toString() == 'https://gitlab.example.com/api/v4/projects/123/packages/maven'
        """
    )

    @Test
    fun `JVM convention makes benchmarks available during configuration`() = checkBuild(
        """
        plugins { id 'com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions' }
        assert sourceSets.names.contains('benchmarks')
        assert benchmark.targets.names == ['benchmarks'] as Set
        dependencies { benchmarksImplementation files('extra.jar') }
        assert configurations.benchmarksImplementation.dependencies.any { it.group == 'org.jetbrains.kotlinx' }
        """
    )

    @Test
    fun `JVM function can select main without creating a separate source set`() = checkBuild(
        """
        import com.huanshankeji.SourceSetType
        import static com.huanshankeji.benchmark.KotlinxBenchmarkJvmKt.configureKotlinxBenchmarkJvm
        plugins { id 'com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions' apply false }
        configureKotlinxBenchmarkJvm(project, SourceSetType.Main)
        assert !sourceSets.names.contains('benchmarks')
        assert benchmark.targets.names == ['main'] as Set
        assert configurations.implementation.dependencies.any { it.group == 'org.jetbrains.kotlinx' }
        """
    )

    @Test
    fun `multiplatform benchmarks follow targets added by the consumer`() = checkBuild(
        """
        plugins { id 'com.huanshankeji.benchmark.kotlinx-benchmark-multiplatform-conventions' }
        kotlin { jvm(); js { nodejs() } }
        assert benchmark.targets.names.containsAll(['jvm', 'js'])
        """
    )
}
