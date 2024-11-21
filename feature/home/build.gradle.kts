plugins {
    alias(libs.plugins.sqwads.android.feature)
    alias(libs.plugins.sqwads.android.library.compose)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.feature.home"

}

dependencies {

    implementation(projects.core.design)
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.androidx.navigation.compose)

}