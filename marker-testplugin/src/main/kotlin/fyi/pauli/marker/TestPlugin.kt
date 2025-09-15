package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.control.CircleController
import fyi.pauli.marker.control.CuboidController
import fyi.pauli.marker.control.LineController
import fyi.pauli.marker.control.MarkerController
import fyi.pauli.marker.control.tool.ColorPicker
import fyi.pauli.marker.listener.HotbarScrollListener
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color

class TestPlugin : JavaPlugin(), Listener {
    var controllers = ArrayList<MarkerController>()
    var colorPicker = ColorPicker()

    companion object {
        lateinit var INSTANCE: TestPlugin; private set
    }

    override fun onLoad() {
        ModelCache.setupModelCache()
    }

    override fun onEnable() {
        INSTANCE = this
        server.pluginManager.registerEvents(this, this)
        server.pluginManager.registerEvents(HotbarScrollListener(this), this)

        controllers.add(LineController(this))
        controllers.add(CuboidController(this))
        controllers.add(CircleController(this))

        Bukkit.getServer().scheduler.runTaskTimer(this, Runnable {
            Bukkit.getServer().onlinePlayers.forEach { player ->
                val color: Color = colorPicker.color()
                val formattedColor = String.format("#%02X%02X%02X", color.red, color.green, color.blue)
                player.sendActionBar(
                    MiniMessage.miniMessage()
                        .deserialize("<gray>Color: <$formattedColor>$formattedColor</gray>")
                )
            }
        }, 0, 1)
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

        inv.setItem(8, colorPicker.itemStack())
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }