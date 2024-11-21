plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.feature.login"

}

dependencies {

    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.compose)


}