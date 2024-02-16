plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.foodorderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodorderapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding  = true
    }
}

dependencies {

    // Circle Imageview
    implementation ("de.hdodenhof:circleimageview:3.0.0")
    // Room database
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
   // implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database")

    //event bus
    implementation ("org.greenrobot:eventbus:3.0.0")
    // Glide load image
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    // Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    // MaterialDialog
    implementation ("com.afollestad.material-dialogs:core:0.9.6.0")
    // Indicator
    implementation ("me.relex:circleindicator:2.1.6")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}