import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
    alias(libs.plugins.sqwads.android.application.firebase)
}

android {
    namespace = "com.psyluckco.sqwads.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.timber)
    implementation(project(":core:google"))
    implementation(project(":core:firebase"))
}