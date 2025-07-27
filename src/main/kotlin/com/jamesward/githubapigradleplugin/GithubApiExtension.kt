package com.jamesward.githubapigradleplugin

import kotlinx.coroutines.runBlocking
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import javax.inject.Inject



abstract class GithubApiExtension {
    abstract val owner: Property<String>
    abstract val repo: Property<String>
    abstract val token: Property<String>

    val releases: Provider<List<GithubRelease>> = getProject()?.provider {
        val githubApiService = GithubApiService(this)
        runBlocking { githubApiService.fetchReleases() }
    } ?: throw IllegalStateException("Project must be set")

    @Inject
    protected abstract fun getProject(): Project?
}
