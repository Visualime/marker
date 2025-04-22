plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.plugin.serialization) apply false

  alias(libs.plugins.paperweight.userdev) apply false
}

allprojects {
  group = "fyi.pauli"
  version = "0.0.1"
}