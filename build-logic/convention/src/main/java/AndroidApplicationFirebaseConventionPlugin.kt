import com.psyluckco.sqwads.build_logic.convention.implementation
import com.psyluckco.sqwads.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Created by developer on 07-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                implementation(libs.findLibrary("firebase-auth").get())
                implementation(libs.findLibrary("firebase-firestore").get())
                implementation(libs.findLibrary("firebase-analytics").get())
                implementation(libs.findLibrary("firebase-database").get())
                implementation(libs.findLibrary("play-services-auth").get())
                implementation(libs.findLibrary("firebase-crashlytics").get())

            }
        }
    }
}