import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.paper.yml)
    alias(libs.plugins.paper.run)
}

repositories {
    mavenCentral()
    maven("https://repo.pauli.fyi/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraftVersion.get()}-R0.1-SNAPSHOT")
    paperLibrary(kotlin("stdlib"))

    paperLibrary("fyi.pauli", "marker-api", "0.0.7")
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
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}
