package fyi.pauli.marker.tests

import fyi.pauli.marker.Plugin
import fyi.pauli.marker.Test
import fyi.pauli.marker.cachedModelsPath
import fyi.pauli.marker.parseAllModels
import org.bukkit.command.CommandSender
import kotlin.io.path.listDirectoryEntries

object ModelCoverageTest : Test() {
    override val name: String = "model-coverage"

    override fun start(sender: CommandSender) {
        val models = Plugin.parseAllModels

        val cachedModelsSize = Plugin.cachedModelsPath.listDirectoryEntries().size

        if (models.size == cachedModelsSize) {
            sender.sendMessage("All models were converted successfully.")
        } else {
            sender.sendMessage("Not all models were converted successfully. [${models.size} / $cachedModelsSize]")
        }
    }
}