import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.application)
    alias(libs.plugins.sqwads.android.application.compose)
    alias(libs.plugins.sqwads.android.hilt)
    alias(libs.plugins.sqwads.android.application.firebase)
}

android {
    namespace = "com.psyluckco.sqwads"

    defaultConfig {
        applicationId = "com.psyluckco.sqwads"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

}

dependencies {
    implementation(projects.feature.register)
    implementation(projects.feature.login)
    implementation(projects.feature.home)

    implementation(projects.core.firebase)
    implementation(projects.core.design)
    implementation(projects.core.common)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.timber)
}