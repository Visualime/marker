rootProject.name = "marker"

include(
    "${rootProject.name}-api",
    "${rootProject.name}-bootstrap",
    "${rootProject.name}-testplugin",
)

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
