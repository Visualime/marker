package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.geometry.Line
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.java.JavaPlugin

class TestPlugin : JavaPlugin(), Listener {

    companion object {
        lateinit var INSTANCE: TestPlugin; private set
    }

    override fun onLoad() {
        ModelCache.setupModelCache()
    }

    override fun onEnable() {
        INSTANCE = this
        server.pluginManager.registerEvents(this, this)
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }