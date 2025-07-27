package com.jamesward.githubapigradleplugin

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.io.IOException


class GithubApiService(private val extension: GithubApiExtension) {

    private val apiUrl = "https://api.github.com"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
            })
        }
    }

    suspend fun fetchReleases(): List<GithubRelease> {
        if (extension.owner.orNull == null) throw IllegalStateException("owner must be specified")
        if (extension.repo.orNull == null) throw IllegalStateException("repo must be specified")

        val url = "${apiUrl}/repos/${extension.owner.get()}/${extension.repo.get()}/releases"

        val response = client.get(url) {
            applyCommonHeaders()
        }

        if (!response.status.isSuccess()) {
            throw IOException("Failed to fetch releases: ${response.status.value} ${response.status.description}")
        }

        return response.body()
    }

    private fun HttpRequestBuilder.applyCommonHeaders() {
        extension.token.orNull?.let { token ->
            header("Authorization", "token $token")
        }

        header("Accept", "application/vnd.github.v3+json")
        header("User-Agent", "GithubApiGradlePlugin")
    }
}
