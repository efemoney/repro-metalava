@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://androidx.dev/metalava/builds/12996812/artifacts/repo/m2repository") {
      content { includeGroup("com.android.tools.metalava") }
    }
  }
}

rootProject.name = "metalava-repro"

include(
  ":app",
  "lib-a",
  "lib-b",
)

project(":lib-a").buildFileName = "liba.gradle.kts"
project(":lib-b").buildFileName = "libb.gradle.kts"
