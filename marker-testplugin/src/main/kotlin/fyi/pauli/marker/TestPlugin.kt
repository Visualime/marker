package fyi.pauli.marker

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class TestPlugin : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: TestPlugin; private set
    }

    override fun onEnable() {
        INSTANCE = this
        @Suppress("UnstableApiUsage")
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(testCommand())
        }
    }
}

val Plugin by lazy { TestPlugin.INSTANCE }