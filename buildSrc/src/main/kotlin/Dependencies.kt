object Dependencies {
    object Versions {
        const val kotlin = "1.4.0"
        const val ktx = "1.3.1"
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.1"
        const val materialComponents = "1.2.0"
    }

    const val kotlinStandard = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val androidKtx = "androidx.core:core-ktx:${Versions.ktx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    // Designs
    const val materialComponents = "com.google.android.material:material:${Versions.materialComponents}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Testing
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
