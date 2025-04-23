rootProject.name = "marker"

include(
    ":marker-api",
    ":marker-bootstrap",
    ":marker-testplugin"
)

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
