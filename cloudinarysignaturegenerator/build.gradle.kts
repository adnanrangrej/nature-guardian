plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.johnrengelman.shadow)
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
}
repositories {
    mavenCentral()
}
dependencies {
    // Google Cloud Functions Framework Api
    implementation(libs.functions.framework.api)

    // Gson
    implementation(libs.gson)

    // Cloudinary
    implementation(libs.cloudinary.core)
    implementation(libs.cloudinary.http5)
}
