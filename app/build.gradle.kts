plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.sothuchi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sothuchi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.google.android.material:material:1.6.0")
    implementation ("com.github.prolificinteractive:material-calendarview:2.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.daimajia.swipelayout:library:1.2.0@aar")
    implementation ("com.nineoldandroids:library:2.4.0")
}