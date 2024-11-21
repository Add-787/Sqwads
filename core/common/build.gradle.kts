plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.firebase.crashlytics)
//    implementation(libs.androidx.compose.material3)
    implementation(libs.timber)
    implementation(libs.androidx.material3.android)
}