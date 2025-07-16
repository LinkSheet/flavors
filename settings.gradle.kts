@file:Suppress("UnstableApiUsage")

import com.gitlab.grrfe.gradlebuild.maybeConfigureIncludingRootRefreshVersions
import fe.build.dependencies.Grrfe
import fe.build.dependencies._1fexd
import fe.buildsettings.config.MavenRepository
import fe.buildsettings.config.configureRepositories
import fe.buildsettings.extension.hasJitpackEnv
import fe.buildsettings.extension.includeProject

rootProject.name = "flavors"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }

    plugins {
        id("de.fayard.refreshVersions") version "0.60.5"
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
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
    id("org.gradle.toolchains.foojay-resolver-convention")
    id("com.gitlab.grrfe.build-settings-plugin")
}

configureRepositories(
    MavenRepository.Google,
    MavenRepository.MavenCentral,
    MavenRepository.Jitpack,
    MavenRepository.Mozilla,
    MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    mode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
)

maybeConfigureIncludingRootRefreshVersions()

extra.properties["gradle.build.dir"]
    ?.let { includeBuild(it.toString()) }


includeProject(":core", "core")
if(file("apkmirror").exists()) {
    includeProject(":apkmirror", "apkmirror")
}

buildSettings {
    substitutes {
        trySubstitute(Grrfe.std, properties["kotlin-ext.dir"])
        trySubstitute(Grrfe.httpkt, properties["httpkt.dir"])
        trySubstitute(_1fexd.composeKit, properties["composekit.dir"])
    }
}
