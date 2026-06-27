import com.gitlab.grrfe.gradlebuild.Plugins
import com.gitlab.grrfe.gradlebuild.PublicationComponent2
import com.gitlab.grrfe.gradlebuild.PublicationName2
import com.gitlab.grrfe.gradlebuild.Version
import com.gitlab.grrfe.gradlebuild.android.AndroidSdk
import com.gitlab.grrfe.gradlebuild.android.accessor.androidLibraryExtension
import com.gitlab.grrfe.gradlebuild.android.extension.singleVariant
import com.gitlab.grrfe.gradlebuild.applyPlugin
import com.gitlab.grrfe.gradlebuild.extension.isPlatform
import com.gitlab.grrfe.gradlebuild.extension.isTestApp
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library") apply false
    id("com.gitlab.grrfe.android-build-plugin")
    id("com.gitlab.grrfe.library-build-plugin")
    id("org.jetbrains.kotlin.plugin.parcelize") apply false
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


    if (!isTestApp) {
        applyPlugin(Plugins.GrrfeLibraryBuild)
    }

    applyPlugin(
        Plugins.MavenPublish,
        Plugins.GrrfeAndroidBuild,
    )

    group = baseGroup

    if (!isPlatform && !isTestApp) {
        library {
            publication {
                name.set(PublicationName2.Release)
                component.set(if (isPlatform) PublicationComponent2.JavaPlatform else PublicationComponent2.Android)
            }
        }

        kotlinExtension.apply {
            jvmToolchain(Version.JVM)
            explicitApiWarning()
        }

        androidLibraryExtension.apply {
            namespace = subProject.toNamespace()
            compileSdk = 37

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
