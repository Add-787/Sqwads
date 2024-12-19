import com.psyluckco.sqwads.build_logic.convention.implementation

plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.sqwads.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.firebase)
    implementation(libs.timber)

    implementation(libs.firebase.auth)
    implementation(project(":core:google"))
}