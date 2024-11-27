plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.feature.forgot_password"

}

dependencies {
    implementation(projects.core.firebase)
    implementation(libs.androidx.navigation.compose)
}