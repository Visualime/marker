package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.geometry.Line
import fyi.pauli.marker.markers.cubicMarker
import fyi.pauli.marker.markers.lineMarker
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

    var firstCubic: Block? = null
    var firstLinear: Block? = null

    @EventHandler
    fun onClick(event: PlayerInteractEvent) {
        if (event.hand != EquipmentSlot.HAND) return
        if (event.item == null) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        when (event.item?.type) {
            Material.BLAZE_ROD -> {
                if (firstLinear == null) {
                    firstLinear = event.clickedBlock!!
                    event.player.sendMessage("1 / 2")
                    return
                }
                val line = Line(firstLinear!!.location.toCenterLocation(), event.clickedBlock!!.location.toCenterLocation())
                lineMarker(line)

                event.player.sendMessage("2 / 2")

                firstLinear = null
            }

            Material.BREEZE_ROD -> {
                if (firstCubic == null) {
                    firstCubic = event.clickedBlock!!
                    event.player.sendMessage("1 / 2")
                    return
                }
                val line = Line(firstCubic!!.location.toCenterLocation(), event.clickedBlock!!.location.toCenterLocation())
                cubicMarker(line.start, line.end)

                event.player.sendMessage("2 / 2")

                firstCubic = null
            }

            else -> return
        }

    }
}

val Plugin by lazy { TestPlugin.INSTANCE }