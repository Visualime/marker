package fyi.pauli.marker

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack

@Suppress("UnstableApiUsage")
abstract class Test {

    abstract val name: String
    abstract fun start(context: CommandContext<CommandSourceStack>)

}