import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.feature.home"

}

dependencies {

    implementation(projects.core.firebase)
    implementation(libs.androidx.navigation.compose)

}