@file:Suppress("UnstableApiUsage")

import com.gitlab.grrfe.gradlebuild.config.MavenRepository
import com.gitlab.grrfe.gradlebuild.config.configureRepositories
import com.gitlab.grrfe.gradlebuild.extension.includeProject
import com.gitlab.grrfe.gradlebuild.maybeConfigureIncludingRootRefreshVersions
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
        id("net.nemerosa.versioning")
        id("com.android.library")
        id("org.jetbrains.kotlin.android")
    }

    when (val gradleBuildDir = extra.properties["gradle.build.dir"]) {
        null -> {
            val gradleBuildVersion = extra.properties["gradle.build.version"]
            val plugins = extra.properties["gradle.build.plugins"]
                .toString().trim().split(",")
                .map { it.trim().split("=") }
                .filter { it.size == 2 }
                .associate { it[0] to it[1] }

            resolutionStrategy {
                eachPlugin {
                    plugins[requested.id.id]?.let { useModule("$it:$gradleBuildVersion") }
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
    MavenRepository.Google,
    MavenRepository.MavenCentral,
    MavenRepository.Jitpack,
    mode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
)

maybeConfigureIncludingRootRefreshVersions()

extra.properties["gradle.build.dir"]
    ?.let { includeBuild(it.toString()) }


includeProject(":core", "core")
includeProject(":interconnect-core", "interconnect/core")
includeProject(":interconnect-client", "interconnect/client")
includeProject(":interconnect-test-app", "interconnect/test-app")
includeProject(":platform", "platform")

buildSettings {
    substitutes {
        trySubstitute(Grrfe.std, properties["kotlin-ext.dir"])
    }
}
