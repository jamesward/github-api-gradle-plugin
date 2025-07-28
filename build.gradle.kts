import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

@Suppress("UnstableApiUsage")
plugins {
    `kotlin-dsl`

    embeddedKotlin("jvm")
    embeddedKotlin("plugin.power-assert")
    embeddedKotlin("plugin.serialization")

    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "com.jamesward"
version = "0.0.2"

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
    // align transitives to embedded kotlin version
    implementation("io.ktor:ktor-client-core:3.0.3")
    implementation("io.ktor:ktor-client-cio:3.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")

    testImplementation(embeddedKotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    pom {
        name = "GitHub API Gradle Plugin"
        description = "A plugin to use the GitHub v3 REST API from Gradle"
        inceptionYear = "2025"
        url = "https://github.com/jamesward/github-api-gradle-plugin"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
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
        scm {
            url = "https://github.com/jamesward/github-api-gradle-plugin"
            connection = "https://github.com/jamesward/github-api-gradle-plugin.git"
            developerConnection = "scm:git:git@github.com:jamesward/github-api-gradle-plugin.git"
        }
    }
}

