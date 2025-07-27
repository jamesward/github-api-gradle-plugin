package com.jamesward.githubapigradleplugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.kotlin.dsl.create

class GithubApiGradlePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create<GithubApiExtension>("github")
    }
}
