// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.1")
        classpath ("com.android.tools.build:gradle:8.5.0")
    }
}