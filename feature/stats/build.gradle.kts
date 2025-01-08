plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.feature.stats"
}

dependencies {

    implementation(libs.androidx.navigation.compose)
    implementation(libs.vico.compose.m3)
}