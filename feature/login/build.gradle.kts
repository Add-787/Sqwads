import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
    alias(libs.plugins.sqwads.android.application.firebase)
}

android {
    namespace = "com.psyluckco.sqwads.feature.login"

}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(project(":core:firebase"))


}