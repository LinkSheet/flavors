
import com.gitlab.grrfe.gradlebuild.Version
import com.gitlab.grrfe.gradlebuild.android.AndroidSdk
import fe.build.dependencies.Grrfe

plugins {
}

android {
}

dependencies {
    api(project(":sdk-common"))

    api(KotlinX.coroutines.android)
    implementation(Koin.android)
    implementation(Grrfe.std.result.core)
    implementation(Grrfe.std.uri)
    implementation(AndroidX.core.ktx)

    testImplementation(Testing.robolectric)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.test.ext.junit.ktx)
    testImplementation(Grrfe.std.test)
    testImplementation(Grrfe.std.result.assert)
    testImplementation("com.willowtreeapps.assertk:assertk:_")
}
