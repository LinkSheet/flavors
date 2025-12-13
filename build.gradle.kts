import com.gitlab.grrfe.gradlebuild.android.AndroidSdk
import com.gitlab.grrfe.gradlebuild.android.extension.singleVariant
import com.gitlab.grrfe.gradlebuild.common.extension.isPlatform
import com.gitlab.grrfe.gradlebuild.common.extension.isTestApp
import com.gitlab.grrfe.gradlebuild.library.publishing.PublicationComponent2
import com.gitlab.grrfe.gradlebuild.library.publishing.PublicationName2
import com.gitlab.grrfe.gradlebuild.util.accessor.implementationProxy
import fe.build.dependencies.Grrfe
import fe.buildlogic.Plugins
import fe.buildlogic.Version
import fe.buildlogic.accessor.androidLibraryProxy
import fe.buildlogic.accessor.kotlinAndroidProxy
import fe.buildlogic.applyPlugin
import fe.buildlogic.common.CompilerOption
import fe.buildlogic.common.extension.addCompilerOptions
import fe.buildlogic.version.AndroidVersionStrategy

plugins {
    id("org.jetbrains.kotlin.android") apply false
    id("com.android.library") apply false
    id("net.nemerosa.versioning") apply false
    id("com.gitlab.grrfe.new-build-logic-plugin")
    id("com.gitlab.grrfe.library-build-plugin")
}

val baseGroup = "com.github.Linksheet.flavors"

subprojects {
    logger.quiet("Init for $this, isTestApp=$isTestApp, isPlatform=$isPlatform")

    if (!isTestApp && !isPlatform) {
        applyPlugin(Plugins.AndroidLibrary, Plugins.KotlinAndroid)
    }

    applyPlugin(
        Plugins.MavenPublish,
        Plugins.GrrfeNewBuildLogic,
        Plugins.GrrfeLibraryBuild,
        Plugins.NemerosaVersioning
    )

    group = baseGroup
    library {
        val now = System.currentTimeMillis()
        versionStrategy.set(AndroidVersionStrategy(now))

        if (!isTestApp) {
            publication {
                name.set(PublicationName2.Release)
                component.set(if (isPlatform) PublicationComponent2.JavaPlatform else PublicationComponent2.Android)
            }
        }
    }

    if (!isPlatform && !isTestApp) {
        kotlinAndroidProxy().run {
            jvmToolchain(Version.JVM)
            explicitApiWarning()
            addCompilerOptions(CompilerOption.NestedTypeAliases, CompilerOption.SkipPreReleaseCheck)
        }

        androidLibraryProxy().run {
            namespace = baseGroup
            compileSdk = AndroidSdk.COMPILE_SDK

            defaultConfig {
                minSdk = AndroidSdk.MIN_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.unitTests.isIncludeAndroidResources = true
            }

            lint {
                disable.add("EmptyNavDeepLink")
            }

            publishing {
                singleVariant(PublicationName2.Release) {
                    withSourcesJar()
                    // Disable for now https://github.com/Kotlin/dokka/issues/2956#issuecomment-2191606810
//                    withJavadocJar()
                }
            }
        }

        this@subprojects.dependencies {
        }
    }
}
