import com.gitlab.grrfe.gradlebuild.Plugins
import com.gitlab.grrfe.gradlebuild.PublicationComponent2
import com.gitlab.grrfe.gradlebuild.PublicationName2
import com.gitlab.grrfe.gradlebuild.Version
import com.gitlab.grrfe.gradlebuild.android.AndroidSdk
import com.gitlab.grrfe.gradlebuild.android.accessor.androidLibraryExtension
import com.gitlab.grrfe.gradlebuild.android.extension.singleVariant
import com.gitlab.grrfe.gradlebuild.android.version.AndroidVersionStrategy
import com.gitlab.grrfe.gradlebuild.applyPlugin
import com.gitlab.grrfe.gradlebuild.extension.isPlatform
import com.gitlab.grrfe.gradlebuild.extension.isTestApp
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library") apply false
    id("com.gitlab.grrfe.android-build-plugin") apply false
    id("com.gitlab.grrfe.library-build-plugin")
}

val baseGroup = "com.github.Linksheet.flavors"

fun Project.toNamespace() = buildString {
    append(baseGroup)
    append(".")
    append(name.replace("-", ""))
}

subprojects {
    val subProject = this
    logger.quiet("Init for $this, isTestApp=$isTestApp, isPlatform=$isPlatform")

    if (!isTestApp && !isPlatform) {
        applyPlugin(Plugins.AndroidLibrary)
    }

    applyPlugin(
        Plugins.MavenPublish,
        Plugins.GrrfeAndroidBuild,
        Plugins.GrrfeLibraryBuild,
    )

    group = baseGroup
    library {
        versionStrategy.set(AndroidVersionStrategy)

        if (!isTestApp) {
            publication {
                name.set(PublicationName2.Release)
                component.set(if (isPlatform) PublicationComponent2.JavaPlatform else PublicationComponent2.Android)
            }
        }
    }

    if (!isPlatform && !isTestApp) {
        kotlinExtension.apply {
            jvmToolchain(Version.JVM)
            explicitApiWarning()
        }

        androidLibraryExtension.apply {
            namespace = subProject.toNamespace()
            compileSdk = AndroidSdk.COMPILE_SDK

            defaultConfig {
                minSdk = 24
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.unitTests.isIncludeAndroidResources = true
            }

            lint { disable.add("EmptyNavDeepLink") }

            publishing {
                singleVariant(PublicationName2.Release) {
                    if (this@subprojects.name != "interconnect-core") {
                        withSourcesJar()
                    }
                    // Disable for now https://github.com/Kotlin/dokka/issues/2956#issuecomment-2191606810
//                    withJavadocJar()
                }
            }
        }

        this@subprojects.dependencies {}
    }
}
