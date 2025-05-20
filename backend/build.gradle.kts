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
    mavenCentral() // Common for Java/Kotlin dependencies
}
dependencies {

    // aws lambda
    implementation(libs.aws.lambda.java.core)

    // aws dynamodb
    implementation(libs.dynamodb.jvm)

    // aws sns
    implementation(libs.sns.jvm)

    // Gson
    implementation(libs.gson)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}
