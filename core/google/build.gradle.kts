plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.google"
    compileSdk = 34

    defaultConfig {
        multiDexEnabled = true
    }
}

dependencies {
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}