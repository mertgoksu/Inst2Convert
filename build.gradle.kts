// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.chaquo.python") version "16.0.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://chaquo.com/maven")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0")
        classpath ("com.chaquo.python:gradle:16.0.0")
    }
}
