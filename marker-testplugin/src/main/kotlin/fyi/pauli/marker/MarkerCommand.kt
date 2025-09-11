package fyi.pauli.marker

import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import fyi.pauli.marker.markers.LineMarker
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

fun markerCommand(): LiteralCommandNode<CommandSourceStack> {
    val linear = Commands.literal("linear")
        .executes {
            val player = it.source.sender as Player
            val marker = LineMarker(
                Line(player.location, player.location.clone().add(2.0, 2.0, 2.0)),
            )

            marker.create(player.location)
            Command.SINGLE_SUCCESS
        }

    return Commands.literal("marker")
        .then(linear)
        .build()
}