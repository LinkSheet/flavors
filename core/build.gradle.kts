import fe.build.dependencies.Grrfe

plugins {
}
dependencies {
    implementation(AndroidX.core.ktx)

    testImplementation(kotlin("test"))
    testImplementation("com.willowtreeapps.assertk:assertk:_")
    testImplementation(Grrfe.std.test)
}
