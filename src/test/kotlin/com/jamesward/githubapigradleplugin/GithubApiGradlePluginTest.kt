package com.jamesward.githubapigradleplugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
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
        val releases = project.extensions.getByType(GithubApiExtension::class.java).releases
        assert(releases.orNull?.isNotEmpty() ?: false)
    }

    /*
    @Test
    fun `plugin registers tasks`() {
        // Verify that all tasks are registered
        assertNotNull(project.tasks.findByName("greeting"), "greeting task should be registered")
        assertNotNull(project.tasks.findByName("listGithubReleases"), "listGithubReleases task should be registered")
        assertNotNull(project.tasks.findByName("fetchLatestGithubRelease"), "fetchLatestGithubRelease task should be registered")
        assertNotNull(project.tasks.findByName("fetchGithubReleaseByTag"), "fetchGithubReleaseByTag task should be registered")
    }

    @Test
    fun `fetchReleases returns list of releases`() {
        // Create a mock response with a list of releases
        val releases = listOf(
            createMockRelease(1, "v1.0.0", "First Release"),
            createMockRelease(2, "v1.1.0", "Second Release")
        )

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(releases))

        mockWebServer.enqueue(mockResponse)

        // Create a service with our configured extension
        val extension = project.extensions.getByType(GithubApiExtension::class.java)
        val service = GithubApiService(extension)

        // Fetch releases and verify the result
        val result = service.fetchReleases()

        assertEquals(2, result.size, "Should return 2 releases")
        assertEquals("v1.0.0", result[0].tagName, "First release should have tag v1.0.0")
        assertEquals("v1.1.0", result[1].tagName, "Second release should have tag v1.1.0")
    }

    @Test
    fun `fetchLatestRelease returns latest release`() {
        // Create a mock response with a single release
        val release = createMockRelease(1, "v1.0.0", "Latest Release")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(release))

        mockWebServer.enqueue(mockResponse)

        // Create a service with our configured extension
        val extension = project.extensions.getByType(GithubApiExtension::class.java)
        val service = GithubApiService(extension)

        // Fetch the latest release and verify the result
        val result = service.fetchLatestRelease()

        assertNotNull(result, "Should return a release")
        assertEquals("v1.0.0", result.tagName, "Release should have tag v1.0.0")
        assertEquals("Latest Release", result.name, "Release should have correct name")
    }

    @Test
    fun `fetchReleaseByTag returns release with matching tag`() {
        // Create a mock response with a single release
        val release = createMockRelease(1, "v1.0.0", "Tagged Release")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(release))

        mockWebServer.enqueue(mockResponse)

        // Create a service with our configured extension
        val extension = project.extensions.getByType(GithubApiExtension::class.java)
        val service = GithubApiService(extension)

        // Fetch release by tag and verify the result
        val result = service.fetchReleaseByTag("v1.0.0")

        assertNotNull(result, "Should return a release")
        assertEquals("v1.0.0", result.tagName, "Release should have tag v1.0.0")
        assertEquals("Tagged Release", result.name, "Release should have correct name")
    }

    @Test
    fun `fetchReleaseByTag returns null for non-existent tag`() {
        // Create a mock 404 response for a non-existent tag
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("{\"message\":\"Not Found\"}")

        mockWebServer.enqueue(mockResponse)

        // Create a service with our configured extension
        val extension = project.extensions.getByType(GithubApiExtension::class.java)
        val service = GithubApiService(extension)

        // Fetch release by non-existent tag and verify the result is null
        val result = service.fetchReleaseByTag("non-existent-tag")

        assertNull(result, "Should return null for non-existent tag")
    }

    @Test
    fun `extension validation throws exception for missing owner`() {
        val extension = GithubApiExtension()
        extension.repo = "testrepo"

        val exception = assertFailsWith<IllegalStateException> {
            extension.validate()
        }

        assertEquals("GitHub repository owner must be specified", exception.message)
    }

    @Test
    fun `extension validation throws exception for missing repo`() {
        val extension = GithubApiExtension()
        extension.owner = "testuser"

        val exception = assertFailsWith<IllegalStateException> {
            extension.validate()
        }

        assertEquals("GitHub repository name must be specified", exception.message)
    }

    // Helper method to create a mock GitHub release
    private fun createMockRelease(id: Long, tagName: String, name: String): GithubRelease {
        return GithubRelease(
            id = id,
            url = "https://api.github.com/repos/testuser/testrepo/releases/$id",
            htmlUrl = "https://github.com/testuser/testrepo/releases/tag/$tagName",
            tagName = tagName,
            name = name,
            draft = false,
            prerelease = false,
            createdAt = "2023-01-01T00:00:00Z",
            publishedAt = "2023-01-01T00:00:00Z",
            body = "Release notes for $name",
            assets = listOf(
                GithubReleaseAsset(
                    id = id * 100,
                    url = "https://api.github.com/repos/testuser/testrepo/releases/assets/${id * 100}",
                    browserDownloadUrl = "https://github.com/testuser/testrepo/releases/download/$tagName/asset-$id.zip",
                    name = "asset-$id.zip",
                    label = "Asset $id",
                    state = "uploaded",
                    size = 1024 * id,
                    createdAt = "2023-01-01T00:00:00Z",
                    updatedAt = "2023-01-01T00:00:00Z"
                )
            )
        )
    }
     */
}
