rootProject.name = "marker"

include(
  ":api",
  ":bootstrap",
)

pluginManagement {
  repositories {
    mavenLocal()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
  }
}
