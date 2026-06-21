import fe.build.dependencies.Grrfe

plugins {
    id("kotlin-parcelize")
}

android {
}

kotlin {
    with(compilerOptions.freeCompilerArgs) {
//        addAll(KotlinCompilerArgs.createPluginOptions(PluginOption.Parcelize.ExperimentalCodeGeneration to true))
    }
}

dependencies {
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
