import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.sqwads.android.library)
    alias(libs.plugins.sqwads.android.hilt)
}

android {
    namespace = "com.psyluckco.google"
    compileSdk = 34

    buildFeatures.buildConfig = true
    
    defaultConfig {
        multiDexEnabled = true
        val oauthClientId = localProperties["OAUTH_CLIENT_ID"] as String? ?: ""
        buildConfigField("String", "OAUTH_CLIENT_ID", "\"$oauthClientId\"")
        val nonce = localProperties["NONCE"] as String? ?: "default_nonce"
        buildConfigField("String", "NONCE", "\"$nonce\"")

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