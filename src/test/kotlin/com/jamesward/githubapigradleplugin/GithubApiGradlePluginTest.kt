package com.jamesward.githubapigradleplugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.*

class GithubApiGradlePluginTest {
    private lateinit var project: Project

    @BeforeTest
    fun setup() {
        project = ProjectBuilder.builder().build()
        project.plugins.apply("com.jamesward.github-api-gradle-plugin")

        project.extensions.configure<GithubApiExtension> {
            owner.set("jamesward")
            repo.set("pklgha")
        }
    }

    @Test
    fun `plugin registers extension`() {
        val extension = project.extensions.findByName("github")
        assert(extension is GithubApiExtension)
    }

    @Test
    fun `releases exist`() {
        val releases = project.extensions.getByType<GithubApiExtension>().releases
        assert(releases.orNull?.isNotEmpty() ?: false)
    }
}
