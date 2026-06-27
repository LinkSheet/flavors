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
    implementation(Grrfe.std.result.core.withVersion("0.0.165"))
    implementation(Grrfe.std.uri.withVersion("0.0.165"))
    implementation(AndroidX.core.ktx)


    testImplementation(Testing.robolectric)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(AndroidX.test.ext.junit.ktx)
    testImplementation(Grrfe.std.test.withVersion("0.0.165"))
    testImplementation(Grrfe.std.result.assert.withVersion("0.0.165"))
    testImplementation("com.willowtreeapps.assertk:assertk:_")
}
