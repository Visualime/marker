import com.vanniktech.maven.publish.KotlinJvm

plugins {
    signing
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.maven.publish)
}

version = "0.0.7"
description = "Marker library for minecraft"

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraftVersion.get()}-R0.1-SNAPSHOT")

    api(kotlin("stdlib"))

    api(libs.kotlinx.io)
    api(libs.kotlinx.serialization.json)

    api(libs.bundles.ktor.client)
}

publishing {
    repositories {
        maven("https://repo.unknowncity.de/releases") {
            name = "unknowncity"
            credentials {
                password = System.getenv("MVN_REPO_USERNAME")
                username = System.getenv("MVN_REPO_PASSWORD")
            }
        }

        maven("https://repo.pauli.fyi/releases") {
            name = "pauli"
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    configure(KotlinJvm())

    publishToMavenCentral(false)
    signAllPublications()

    pom {
        name = project.name
        description = project.description

        developers {
            developer { name = "kxmpxtxnt" }
            developer { name = "TheZexquex" }
        }

        licenses {
            license {
                name = "GNU General Public License 3"
                url = "https://www.gnu.org/licenses/gpl-3.0.txt"
            }
        }

        url = "https://github.com/Visualime/marker"

        scm {
            connection = "scm:git:git://github.com/Visualime/marker"
            url = "https://github.com/Visualime/marker/tree/main"
        }
    }
}