import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)

    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.paper.yml)
    alias(libs.plugins.paper.run)

    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
    maven("https://repo.pauli.fyi/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraftVersion.get()}-R0.1-SNAPSHOT")
    paperLibrary(kotlin("stdlib"))
    paperLibrary(libs.kotlinx.serialization.json)

    implementation(project(":marker-api"))

}

paper {
    name = "marker-testplugin"
    version = project.version.toString()
    description = "Plugin used to test certain features of the api."
    authors = listOf("kxmpxtxnt", "TheZexquex")
    main = "fyi.pauli.marker.TestPlugin"
    loader = "fyi.pauli.marker.TestPluginLoader"
    generateLibrariesJson = true
    apiVersion = libs.versions.minecraftVersion.get().substringBeforeLast('.')

    serverDependencies {
        register("marker-bootstrap") {
            required = false // Required if you plan to use advanced features
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}
tasks {
    assemble {
        dependsOn(reobfJar)
    }

    generatePaperPluginDescription {
        useDefaultCentralProxy()
    }
}