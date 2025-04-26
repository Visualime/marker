plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)

    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.paper.yml)
    alias(libs.plugins.paper.run)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraftVersion.get()}-R0.1-SNAPSHOT")

    paperLibrary(kotlin("stdlib"))

    paperLibrary(libs.logback)

    paperLibrary(libs.kotlinx.io)
    paperLibrary(libs.kotlinx.serialization.json)

    paperLibrary(libs.ktor.serialization.json)
    paperLibrary(libs.bundles.ktor.client)

    testImplementation(kotlin("test"))
    testImplementation(project(":marker-api"))
    testImplementation(libs.mockbukkit)
    testImplementation(libs.kotlinx.coroutines.core)
}

paper {
    name = "marker-bootstrap"
    version = project.version.toString()
    description = "Bootstrap of the marker lib."
    authors = listOf("kxmpxtxnt", "TheZexquex")
    main = "fyi.pauli.marker.Bootstrap"
    loader = "fyi.pauli.marker.BootstrapLoader"
    generateLibrariesJson = true
    apiVersion = libs.versions.minecraftVersion.get().substringBeforeLast('.')
}

tasks.test {
    useJUnitPlatform()
}