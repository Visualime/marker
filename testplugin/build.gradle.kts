plugins {
  kotlin("jvm")
  id("io.papermc.paperweight.userdev")
  id("xyz.jpenilla.run-paper") version "2.3.1"
  id("de.eldoria.plugin-yml.paper") version "0.7.0"
}

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  implementation(rootProject)
  paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
  implementation("net.axay", "kspigot", "1.21.0")
}

paper {
  name = "Marker-Test"
  version = "1.0"
  description = "Test mod for the marker lib."
  author = "kxmpxtxnt"
  main = "fyi.pauli.marker.TestPlugin"
  loader = "fyi.pauli.marker.TestPluginLoader"
  generateLibrariesJson = true
  apiVersion = "1.21"
}
