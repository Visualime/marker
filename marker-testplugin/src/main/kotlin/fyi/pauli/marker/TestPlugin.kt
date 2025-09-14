package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.control.LineController
import fyi.pauli.marker.control.MarkerController
import io.ktor.events.Events
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class TestPlugin : JavaPlugin(), Listener {
    var controllers = ArrayList<MarkerController>()
    companion object {
        lateinit var INSTANCE: TestPlugin; private set
    }

    override fun onLoad() {
        ModelCache.setupModelCache()
    }

    override fun onEnable() {
        INSTANCE = this
        server.pluginManager.registerEvents(this, this)

        controllers.add(LineController(this))
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action == Action.LEFT_CLICK_BLOCK) {
            controllers.forEach { controller ->
                if (controller.isUsed(event.item)) {
                    event.setCancelled(true)
                    if (player.isSneaking) {
                        controller.handleDespawn()
                        return@forEach
                    }
                    controller.handleClick(player, event.clickedBlock!!.location)
                }
            }
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val inv = event.player.inventory

        inv.clear()
        controllers.forEach { controller -> inv.addItem(controller.itemStack()) }
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }