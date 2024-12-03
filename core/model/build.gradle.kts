plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.psyluckco.sqwads.core.model"
}

dependencies {

    implementation(libs.firebase.firestore.ktx)
}