// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.android.library") version "8.11.1" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
