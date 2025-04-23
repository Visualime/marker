package fyi.pauli.marker

import org.bukkit.entity.Display

/**
 * Abstract representation of an ingame marker.
 */
abstract class Marker<D : Display> {

    val displayEntities: MutableSet<D> = mutableSetOf()


}