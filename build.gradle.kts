plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    alias(libs.plugins.gms) apply false

    alias(libs.plugins.compose.compiler) apply true
    alias(libs.plugins.plugin.serialization) apply true
}




























