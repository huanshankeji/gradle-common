package com.huanshankeji

import com.huanshankeji.benchmark.KotlinxBenchmarkConventionsExtension
import com.huanshankeji.github.packages.maven.GITHUB_PACKAGES_DEFAULT_REPOSITORY_NAME
import kotlinx.benchmark.gradle.BenchmarksExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.publish.PublishingExtension
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AfterEvaluateReplacementTest {
    private fun project(name: String = "p"): Project =
        ProjectBuilder.builder().withName(name).build()

    private fun Project.evaluate() {
        (this as ProjectInternal).evaluate()
    }

    @Test
    fun `deprecated GitHub Packages plugin wires the repository URL lazily`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.github-packages-maven-publish")

        val extension = project.extensions.getByType(Github_packages_maven_publish_gradle.Extension::class.java)
        extension.owner.set("octocat")
        extension.repository.set("hello-world")

        val repository = project.extensions.getByType(PublishingExtension::class.java)
            .repositories.getByName(GITHUB_PACKAGES_DEFAULT_REPOSITORY_NAME) as MavenArtifactRepository
        assertEquals(
            "https://maven.pkg.github.com/octocat/hello-world",
            repository.url.toString().trimEnd('/')
        )
    }

    @Test
    fun `deprecated GitLab plugin wires the repository URL lazily`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.gitlab-project-level-maven-endpoint-publish")

        val extension =
            project.extensions.getByType(Gitlab_project_level_maven_endpoint_publish_gradle.Extension::class.java)
        extension.host.set("gitlab.example")
        extension.projectId.set("42")

        val repository = project.extensions.getByType(PublishingExtension::class.java)
            .repositories.getByName("GitLab") as MavenArtifactRepository
        assertEquals(
            "https://gitlab.example/api/v4/projects/42/packages/maven",
            repository.url.toString().trimEnd('/')
        )
    }

    @Test
    fun `JVM benchmark conventions apply RegisterSeparate immediately when the function is called`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions")
        project.extensions.getByType(KotlinxBenchmarkConventionsExtension::class.java)
            .sourceSetType(SourceSetType.RegisterSeparate)

        assertNotNull(project.extensions.getByType(org.gradle.api.tasks.SourceSetContainer::class.java).findByName("benchmarks"))
        assertNotNull(project.extensions.getByType(BenchmarksExtension::class.java).targets.findByName("benchmarks"))
        project.evaluate()
    }

    @Test
    fun `JVM benchmark conventions apply Main immediately when the function is called`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions")
        project.extensions.getByType(KotlinxBenchmarkConventionsExtension::class.java)
            .sourceSetType(SourceSetType.Main)

        assertNull(project.extensions.getByType(org.gradle.api.tasks.SourceSetContainer::class.java).findByName("benchmarks"))
        assertNotNull(project.extensions.getByType(BenchmarksExtension::class.java).targets.findByName("main"))
        project.evaluate()
    }

    @Test
    fun `JVM benchmark conventions fail after evaluate if sourceSetType is not called`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions")

        val error = assertFailsWith<IllegalStateException> { project.evaluate() }
        assertTrue(error.message!!.contains("sourceSetType"))
    }

    @Test
    fun `multiplatform benchmark conventions register targets as Kotlin targets are added`() {
        val project = project()
        project.pluginManager.apply("com.huanshankeji.benchmark.kotlinx-benchmark-multiplatform-conventions")

        val benchmarks = project.extensions.getByType(BenchmarksExtension::class.java)
        assertNull(benchmarks.targets.findByName("jvm"))

        project.extensions.getByType(KotlinMultiplatformExtension::class.java).jvm()
        assertNotNull(benchmarks.targets.findByName("jvm"))
        assertNull(benchmarks.targets.findByName("metadata"))
    }
}
