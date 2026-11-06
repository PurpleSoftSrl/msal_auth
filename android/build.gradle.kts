import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "com.example.msal_auth"
version = "1.0-SNAPSHOT"

val msalVersion = "8.3.+"

buildscript {
    val kotlinVersion = "2.2.20"
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.11.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

rootProject.allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1")
        }
    }
}

plugins {
    id("com.android.library")
}

// AGP 9 compiles Kotlin itself (built-in Kotlin) and refuses this plugin.
val agpMajorVersion = com.android.Version.ANDROID_GRADLE_PLUGIN_VERSION.substringBefore('.').toInt()
if (agpMajorVersion < 9) {
    apply(plugin = "kotlin-android")
}

android {
    namespace = "com.example.msal_auth"

    compileSdk = 36

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
        getByName("test") {
            java.srcDirs("src/test/kotlin")
        }
    }

    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation("com.microsoft.identity.client:msal:${msalVersion}")
    implementation("com.google.code.gson:gson:2.14.0")
}
