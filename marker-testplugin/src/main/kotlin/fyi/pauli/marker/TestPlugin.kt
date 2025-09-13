package fyi.pauli.marker

import com.destroystokyo.paper.MaterialTags
import fyi.pauli.marker.caching.ModelCache
import fyi.pauli.marker.geometry.Line
import fyi.pauli.marker.markers.LineMarker
import fyi.pauli.marker.markers.cubicMarker
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
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

    var first: Block? = null

    val concretes = Material.entries.toList().filter { MaterialTags.CONCRETES.isTagged(it) }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (first == null) {
            first = event.block
            event.player.sendMessage("First!")
            return
        }

        val line = Line(first!!.location.toCenterLocation(), event.block.location.toCenterLocation())
        cubicMarker(line) {
            material = concretes.random()
            color = NamedTextColor.NAMES.values().random()
        }

        first = null
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }