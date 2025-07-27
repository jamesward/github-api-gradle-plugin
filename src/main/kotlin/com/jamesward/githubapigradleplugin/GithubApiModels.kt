package com.jamesward.githubapigradleplugin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRelease(
    val id: Long,
    val url: String,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("tag_name") val tagName: String,
    val name: String? = null,
    val draft: Boolean,
    val prerelease: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("published_at") val publishedAt: String? = null,
    val body: String? = null,
    val assets: List<GithubReleaseAsset> = emptyList()
)

@Serializable
data class GithubReleaseAsset(
    val id: Long,
    val url: String,
    @SerialName("browser_download_url") val browserDownloadUrl: String,
    val name: String,
    val label: String? = null,
    val state: String,
    val size: Long,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)
