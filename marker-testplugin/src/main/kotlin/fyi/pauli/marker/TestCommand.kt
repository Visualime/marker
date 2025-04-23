package fyi.pauli.marker

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import fyi.pauli.marker.tests.ModelCoverageTest
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

@Suppress("UnstableApiUsage")
fun testCommand(): LiteralCommandNode<CommandSourceStack> {
    val tests = listOf<Test>(
        ModelCoverageTest
    )

    val featureArgument = Commands.argument("feature", StringArgumentType.string())
        .suggests { _, builder ->
            tests.map(Test::name).forEach(builder::suggest)
            builder.buildFuture()
        }
        .executes { ctx ->
            val test = tests.find { it.name == ctx.getArgument("feature", String::class.java) }

            if (test == null) {
                ctx.source.sender.sendMessage("This test does not exist.")
                return@executes -1
            }

            test.start(ctx.source.sender)
            Command.SINGLE_SUCCESS
        }.build()

    return Commands.literal("test")
        .then(Commands.literal("feature").then(featureArgument))
        .build()
}