import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    `kotlin-dsl`
    kotlin("plugin.power-assert") version embeddedKotlinVersion
    kotlin("plugin.serialization") version embeddedKotlinVersion

    `maven-publish`
    signing
    id("org.danilopianini.publish-on-central") version "9.0.8"
}

group = "com.jamesward"
version = "0.0.1"

gradlePlugin {
    website = "https://github.com/jamesward/github-api-gradle-plugin"
    vcsUrl = "https://github.com/jamesward/github-api-gradle-plugin.git"
    plugins {
        create("GithubApiGradlePlugin") {
            id = "com.jamesward.github-api-gradle-plugin"
            implementationClass = "com.jamesward.githubapigradleplugin.GithubApiGradlePlugin"
            displayName = "Github API Gradle Plugin"
            description = "A plugin to use the GitHub v3 REST API from Gradle"
            tags = listOf("github", "api")
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("io.ktor:ktor-client-core:3.2.2")
    implementation("io.ktor:ktor-client-cio:3.2.2")
    implementation("io.ktor:ktor-client-content-negotiation:3.2.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3") // must be compatible with Gradle's Kotlin version
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.2")

    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

signing {
    sign(publishing.publications)
    useInMemoryPgpKeys(System.getenv("OSS_GPG_KEY"), System.getenv("OSS_GPG_PASS"))
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name = "GitHub API Gradle Plugin"
                description = "A plugin to use the GitHub v3 REST API from Gradle"
                url = "https://github.com/jamesward/github-api-gradle-plugin"

                scm {
                    connection = "scm:git:https://github.com/jamesward/github-api-gradle-plugin.git"
                    developerConnection = "scm:git:git@github.com:jamesward/sgithub-api-gradle-plugin.git"
                    url = "https://github.com/jamesward/github-api-gradle-plugin"
                }

                licenses {
                    license {
                        name = "Apache 2.0"
                        url = "https://opensource.org/licenses/Apache-2.0"
                    }
                }

                developers {
                    developer {
                        id = "jamesward"
                        name = "James Ward"
                        email = "james@jamesward.com"
                        url = "https://jamesward.com"
                    }
                }
            }
        }
    }
}
