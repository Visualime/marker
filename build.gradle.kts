import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false

    alias(libs.plugins.paper.yml) apply false
    alias(libs.plugins.paper.run) apply false
    alias(libs.plugins.paperweight.userdev) apply false
}

allprojects {
    group = "fyi.pauli"
    version = "0.0.1"

    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.add("-Xcontext-parameters")
        }
    }
}