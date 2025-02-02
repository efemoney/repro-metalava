@file:Suppress("UnstableApiUsage", "LeakingThis")

plugins {
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.compose)
}

kotlin {
  androidTarget()
  jvm()
  iosArm64()

  sourceSets {
    commonMain.dependencies {
      api(projects.libB)
      implementation(compose.components.resources)
      implementation(compose.runtime)
      implementation(compose.foundation)
    }
    androidMain.dependencies {
      implementation(libs.androidx.core)
      implementation(libs.androidx.compose.material3)
    }
  }
}

android {
  namespace = "com.repro.metalava.liba"
}

compose {
  resources {
    packageOfResClass = "com.repro.metalava.liba"
    customDirectory(
      sourceSetName = kotlin.sourceSets.commonMain.name,
      directoryProvider = layout.projectDirectory.dir(provider { "src/commonRes" })
    )
  }
}

val mlv = configurations.create("metalava")
val mlvVersion = libs.versions.metalava.get()
val ktVersion = libs.versions.kotlin.get()

dependencies { //noinspection UseTomlInstead,GradleDependency
  mlv("com.android.tools.metalava:metalava:$mlvVersion")
}

afterEvaluate {
  // React to the old variant API because that is what kotlin uses
  // This is called when the old variant models (and hence kotlin compilations) are created
  android.libraryVariants.all {
    val variant = this
    val variantSuffix = variant.name.capitalize()
    val target = kotlin.androidTarget()
    val compilation = target.compilations.getByName(variant.name)

    tasks.register<MakeshiftMetalavaTask>("metalava$variantSuffix") {
      filename = "$mlvVersion-$ktVersion.txt"
      mlvClasspath = mlv
      bootClasspath.from(androidComponents.sdkComponents.bootClasspath)
      compileClasspath.from(variant.getCompileClasspath(null))
      compilation.allKotlinSourceSets.forAll {
        sourceRoots.from(it.kotlin.sourceDirectories)
      }
    }
  }
}

abstract class MakeshiftMetalavaTask : DefaultTask() {

  @get:Inject
  abstract val exec: ExecOperations

  @get:Classpath
  abstract val mlvClasspath: ConfigurableFileCollection

  @get:Classpath
  abstract val bootClasspath: ConfigurableFileCollection

  @get:Classpath
  abstract val compileClasspath: ConfigurableFileCollection

  @get:InputFiles
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val sourceRoots: ConfigurableFileCollection

  @get:Optional
  @get:OutputFile
  abstract val filename: Property<String>

  @TaskAction
  fun run() {
    exec.javaexec {
      mainClass = "com.android.tools.metalava.Driver"
      classpath = mlvClasspath
      args = listOf(
        "--api", filename.get(),
        "--java-source", "17",
        "--kotlin-source", "2.1",
        "--classpath", (bootClasspath + compileClasspath).filter(File::exists).joinToString(File.pathSeparator),
        "--source-path", sourceRoots.filter(File::exists).joinToString(File.pathSeparator),
        "--api-compat-annotation", "androidx.compose.runtime.Composable",
        "--format=v4",
        // "--verbose",
      )
    }
  }
}
