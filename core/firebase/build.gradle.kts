import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
    alias(libs.plugins.sqwads.android.application.firebase)
}

android {
    namespace = "com.psyluckco.sqwads.core.firebase"

    defaultConfig {
        multiDexEnabled = true
    }
}

dependencies {
    implementation(projects.core.model)
    implementation("com.google.mlkit:language-id:17.0.6")
    implementation("com.google.mlkit:translate:17.0.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}