import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.HasConfigurableKotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.jetbrains.compose) apply false
}

subprojects {
  pluginManager.withPlugin("com.android.library") {
    configure<LibraryExtension> {
      compileSdk = 35
      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
      }
    }
  }
  pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    configure<KotlinMultiplatformExtension> {
      targets.configureEach {
        this as HasConfigurableKotlinCompilerOptions<*>
        compilerOptions {
          if (this is KotlinJvmCompilerOptions) {
            javaParameters = true
            jvmTarget = JvmTarget.JVM_17
          }
        }
      }
    }
  }
}
