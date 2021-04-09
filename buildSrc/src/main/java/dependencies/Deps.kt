package dependencies

object Deps {
    val kotlin_version = "1.4.32"

    object Kotlin {
        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:1.3.2"
        val appCompat = "androidx.appcompat:appcompat:1.2.0"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

        object Test {
            val extJunit = "androidx.test.ext:junit:1.1.2"
            val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }

    object MaterialDesign {
        val material = "com.google.android.material:material:1.3.0"
    }

    object Koin {
        private val koin_version = "3.0.1-beta-2"
        val base = "io.insert-koin:koin-android:$koin_version"
        val ext = "io.insert-koin:koin-android-ext:$koin_version"
    }

    object Test {
        val junit4 = "junit:junit:4.13.2"
        private val mockk_version = "1.10.2"
        val mockk = "io.mockk:mockk:$mockk_version"
        val truth = "com.google.truth:truth:1.1"
    }

    object Spek {
        private val spek_version = "2.0.14"
        val spek = "org.spekframework.spek2:spek-dsl-jvm:$spek_version"
        val spekRunner =
            "org.spekframework.spek2:spek-runner-junit5:$spek_version" // testRuntimeOnly
        val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version" // testRuntimeOnly
    }
}
