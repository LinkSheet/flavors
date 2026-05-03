@file:Suppress("UnstableApiUsage")

import com.gitlab.grrfe.gradlebuild.config.configureRepositories
import com.gitlab.grrfe.gradlebuild.extension.includeProject
import com.gitlab.grrfe.gradlebuild.maybeConfigureIncludingRootRefreshVersions
import com.gitlab.grrfe.gradlebuild.repository.MavenRepository
import com.gitlab.grrfe.gradlebuild.repository.google
import com.gitlab.grrfe.gradlebuild.repository.jitpack
import com.gitlab.grrfe.gradlebuild.repository.mavenCentral
import fe.build.dependencies.Grrfe

rootProject.name = "flavors"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }

    plugins {
        id("de.fayard.refreshVersions") version "0.60.6"
        id("com.android.library")
        id("org.jetbrains.kotlin.android")
    }

    when (val gradleBuildDir = extra.properties["gradle.build.dir"]) {
        null -> {
            val gradleBuildVersion = extra.properties["gradle.build.version"]
            resolutionStrategy {
                eachPlugin {
                    with(requested.id) {
                        if (namespace == "com.gitlab.grrfe") {
                            useModule("com.gitlab.grrfe.gradle-build:$name:$gradleBuildVersion")
                        }
                    }
                }
            }
        }
        else -> includeBuild(gradleBuildDir.toString())
    }
}

plugins {
    id("de.fayard.refreshVersions")
    id("com.gitlab.grrfe.settings-build-plugin")
}

configureRepositories(
    MavenRepository.google(),
    MavenRepository.mavenCentral(),
    MavenRepository.jitpack(),
    mode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
)

maybeConfigureIncludingRootRefreshVersions()

extra.properties["gradle.build.dir"]
    ?.let { includeBuild(it.toString()) }


includeProject(":core", "core")
includeProject(":platform", "platform")

buildSettings {
    projects("interconnect") {
        includeProject(":interconnect-core", "core")
        includeProject(":interconnect-client", "client")
        includeProject(":interconnect-test-app", "test-app")
    }

    substitutes {
        trySubstitute(Grrfe.std, properties["kotlin-ext.dir"])
    }
}
