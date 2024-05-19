// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
    }

}
plugins {
    alias(libs.plugins.andoridApplicationPlugin) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
}