package dependencies

object Deps {
    val kotlin_version = "1.5.30"

    object Kotlin {
        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

        private val coroutineVer = "1.5.2"
        val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVer"

        object Test {
            val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVer"
        }
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:1.6.0"
        val appCompat = "androidx.appcompat:appcompat:1.3.1"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.1"
        val nav_version = "2.3.5"
        val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:$nav_version"
        val liveDataLifecycleKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        val liveEvent = "com.github.hadilq:live-event:1.2.3"
        val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"

        object Compose {
            val version = "1.0.3"
            val compose = "androidx.compose.ui:ui:${version}"
            val tooling = "androidx.compose.ui:ui-tooling:${version}"
            val foundation = "androidx.compose.foundation:foundation:${version}"
            val material = "androidx.compose.material:material:${version}"
            val materialIcon = "androidx.compose.material:material-icons-core:${version}"
            val materialIconExtend = "androidx.compose.material:material-icons-extended:${version}"
            val rxJava = "androidx.compose.runtime:runtime-rxjava2:${version}"
            val livedata = "androidx.compose.runtime:runtime-livedata:${version}"
        }

        object Test {
            val coreTesting = "androidx.arch.core:core-testing:2.1.0"
            val extJunit = "androidx.test.ext:junit:1.1.3"
            val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }

    object Epoxy {
        private val epoxyVersion = "4.6.4"
        val epoxy = "com.airbnb.android:epoxy:$epoxyVersion"
        val epoxyProcessor = "com.airbnb.android:epoxy-processor:$epoxyVersion"
        val epoxyDataBinding = "com.airbnb.android:epoxy-databinding:$epoxyVersion"
    }

    object MaterialDesign {
        val material = "com.google.android.material:material:1.4.0"
    }

    object Koin {
        private val koin_version = "3.1.2"
        val base = "io.insert-koin:koin-android:$koin_version"
        val compat = "io.insert-koin:koin-android-compat:$koin_version"
        // val workManger = "io.insert-koin:koin-androidx-workmanager:$koin_version"
        // val compose = "io.insert-koin:koin-androidx-compose:$koin_version"
    }

    object Firebase {
        val bom = "com.google.firebase:firebase-bom:28.1.0"
        val firestoreKtx = "com.google.firebase:firebase-firestore-ktx"
        val auth = "com.google.firebase:firebase-auth"
    }

    object Test {
        val junit4 = "junit:junit:4.13.2"
        private val mockk_version = "1.12.0"
        val mockk = "io.mockk:mockk:$mockk_version"
        val mockkAgent = "io.mockk:mockk-agent-jvm:$mockk_version"
        val truth = "com.google.truth:truth:1.1"
    }

    object Spek {
        private val spek_version = "2.0.17"
        val spek = "org.spekframework.spek2:spek-dsl-jvm:$spek_version"
        val spekRunner =
            "org.spekframework.spek2:spek-runner-junit5:$spek_version" // testRuntimeOnly
        val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version" // testRuntimeOnly

    }

    val flexbox = "com.google.android.flexbox:flexbox:3.0.0"

    val furufuru = "dev.iaiabot.furufuru:furufuru:0.9.1"
}
