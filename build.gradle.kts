import com.gitlab.grrfe.gradlebuild.Plugins
import com.gitlab.grrfe.gradlebuild.PublicationComponent2
import com.gitlab.grrfe.gradlebuild.PublicationName2
import com.gitlab.grrfe.gradlebuild.Version
import com.gitlab.grrfe.gradlebuild.accessor.androidLibraryProxy
import com.gitlab.grrfe.gradlebuild.android.AndroidSdk
import com.gitlab.grrfe.gradlebuild.android.extension.singleVariant
import com.gitlab.grrfe.gradlebuild.android.version.AndroidVersionStrategy
import com.gitlab.grrfe.gradlebuild.applyPlugin
import com.gitlab.grrfe.gradlebuild.common.CompilerOption
import com.gitlab.grrfe.gradlebuild.common.KotlinCompilerArgs
import com.gitlab.grrfe.gradlebuild.extension.isPlatform
import com.gitlab.grrfe.gradlebuild.extension.isTestApp
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

plugins {
    id("com.android.library") apply false
    id("net.nemerosa.versioning") apply false
    id("com.gitlab.grrfe.android-build-plugin") apply false
    id("com.gitlab.grrfe.library-build-plugin")
}

val baseGroup = "com.github.Linksheet.flavors"

subprojects {
    logger.quiet("Init for $this, isTestApp=$isTestApp, isPlatform=$isPlatform")

    if (!isTestApp && !isPlatform) {
        applyPlugin(Plugins.AndroidLibrary)
    }

    applyPlugin(
        Plugins.MavenPublish,
//        Plugins.GrrfeNewBuildLogic,
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
        (kotlinExtension as KotlinAndroidProjectExtension).apply {
            jvmToolchain(Version.JVM)
            explicitApiWarning()
            compilerOptions.freeCompilerArgs.addAll(KotlinCompilerArgs.createCompilerOptions(CompilerOption.NestedTypeAliases, CompilerOption.SkipPreReleaseCheck))
        }

        androidLibraryProxy().run {
            namespace = baseGroup
            compileSdk = AndroidSdk.COMPILE_SDK

            defaultConfig {
                minSdk = 24
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.unitTests.isIncludeAndroidResources = true
            }

            lint { disable.add("EmptyNavDeepLink") }

            publishing {
                singleVariant(PublicationName2.Release) {
                    if(this@subprojects.name != "interconnect-core") {
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
