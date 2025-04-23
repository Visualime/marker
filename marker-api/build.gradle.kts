import java.net.URI

plugins {
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.paperweight.userdev)
}

version = "0.0.7"

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraftVersion.get()}-R0.1-SNAPSHOT")

    implementation(libs.kotlinx.io)
    implementation(libs.kotlinx.serialization.json)
}

publishing {
    repositories {
        maven {
            name = "unknowncity"
            url = URI.create("https://repo.unknowncity.de/releases")
            credentials {
                password = System.getenv("MVN_REPO_USERNAME")
                username = System.getenv("MVN_REPO_PASSWORD")
            }
        }

        maven {
            name = "pauli"
            url = URI.create("https://repo.pauli.fyi/releases")
            credentials(PasswordCredentials::class)
        }
    }

    publications {
        register<MavenPublication>(project.name) {
            from(components["java"])

            this.groupId = project.group.toString()
            this.artifactId = project.name
            this.version = project.version.toString()

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
    }
}
