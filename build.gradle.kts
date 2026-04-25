val generatedVersionCode by extra { (java.util.Date().time / 1000000).toInt() }

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.googleKsp) version libs.versions.ksp apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
}

subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "de.jensklingenberg.ktorfit" && requested.name == "compiler-plugin") {
                useVersion("2.3.3")
                because("Force the latest published Ktorfit compiler plugin compatible with Kotlin 2.3.x")
            }
        }
    }
}