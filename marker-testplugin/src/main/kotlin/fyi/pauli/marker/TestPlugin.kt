package fyi.pauli.marker

import fyi.pauli.marker.caching.ModelCache
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.exitProcess

class TestPlugin : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: TestPlugin; private set
    }

    override fun onLoad() {
        ModelCache.setupModelCache()
    }

    override fun onEnable() {
        INSTANCE = this
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(markerCommand())
        }
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }