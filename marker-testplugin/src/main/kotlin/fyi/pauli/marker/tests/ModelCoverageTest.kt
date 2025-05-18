package fyi.pauli.marker.tests

import com.mojang.brigadier.context.CommandContext
import fyi.pauli.marker.Plugin
import fyi.pauli.marker.Test
import fyi.pauli.marker.cachedModelsPath
import fyi.pauli.marker.parseAllModels
import io.papermc.paper.command.brigadier.CommandSourceStack
import kotlin.io.path.listDirectoryEntries

@Suppress("UnstableApiUsage")
object ModelCoverageTest : Test() {
    override val name: String = "model-coverage"

    override fun start(context: CommandContext<CommandSourceStack>) {
        val models = Plugin.parseAllModels

        val cachedModelsSize = Plugin.cachedModelsPath.listDirectoryEntries().size

        val sender = context.source.sender

        if (models.size == cachedModelsSize) {
            sender.sendMessage("All models were converted successfully.")
        } else {
            sender.sendMessage("Not all models were converted successfully. [${models.size} / $cachedModelsSize].")
        }
    }
}