plugins {

}

android {
}

dependencies {
    api(project(":core"))
    api(project(":interconnect-core"))
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.activity.ktx)
    implementation(KotlinX.coroutines.android)
}
