plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)
    buildToolsVersion(AndroidSdk.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

        testInstrumentationRunner = Dependencies.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.kotlinStandard)
    implementation(Dependencies.androidKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraintLayout)
}
