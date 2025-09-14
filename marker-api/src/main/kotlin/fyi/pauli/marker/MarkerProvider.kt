package fyi.pauli.marker

import org.bukkit.Location
import org.bukkit.entity.Display
import java.util.function.Consumer

interface MarkerProvider<D : Display> {

    fun createEntity(location: Location, builder: Consumer<D>): D
}