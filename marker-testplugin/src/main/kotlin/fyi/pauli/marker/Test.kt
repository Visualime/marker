package fyi.pauli.marker

import org.bukkit.command.CommandSender

abstract class Test {

    abstract val name: String
    abstract fun start(sender: CommandSender)

}