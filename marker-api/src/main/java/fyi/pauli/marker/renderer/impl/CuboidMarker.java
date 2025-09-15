package fyi.pauli.marker.renderer.impl;

import fyi.pauli.marker.provider.BlockDisplayLineMarkerProvider;
import fyi.pauli.marker.provider.LineMarkerProvider;
import fyi.pauli.marker.provider.TextDisplayLineMarkerProvider;
import fyi.pauli.marker.renderer.MultiLineMarker;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class CuboidMarker extends MultiLineMarker<CuboidMarker> {
    protected Alignment alignment = Alignment.EXACT;
    protected final Location startLocation;
    protected final Location endLocation;

    protected CuboidMarker(LineMarkerProvider lineProvider, Location startLocation, Location endLocation) {
        super(lineProvider);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public static CuboidMarker text(Location startLocation, Location endLocation) {
        return new CuboidMarker(TextDisplayLineMarkerProvider.INSTANCE, startLocation, endLocation);
    }

    public static CuboidMarker block(Location startLocation, Location endLocation) {
        return new CuboidMarker(BlockDisplayLineMarkerProvider.INSTANCE, startLocation, endLocation);
    }

    @Override
    public CuboidMarker draw() {
        World world = startLocation.getWorld();

        double minX = Math.min(startLocation.getX(), endLocation.getX());
        double maxX = Math.max(startLocation.getX(), endLocation.getX());
        double minY = Math.min(startLocation.getY(), endLocation.getY());
        double maxY = Math.max(startLocation.getY(), endLocation.getY());
        double minZ = Math.min(startLocation.getZ(), endLocation.getZ());
        double maxZ = Math.max(startLocation.getZ(), endLocation.getZ());

        switch (alignment) {
            case EXACT -> {
                minX = Math.min(startLocation.getX(), endLocation.getX());
                maxX = Math.max(startLocation.getX(), endLocation.getX());
                minY = Math.min(startLocation.getY(), endLocation.getY());
                maxY = Math.max(startLocation.getY(), endLocation.getY());
                minZ = Math.min(startLocation.getZ(), endLocation.getZ());
                maxZ = Math.max(startLocation.getZ(), endLocation.getZ());
            }
            case EXCLUSIVE -> {
                minX = Math.min(startLocation.getBlockX(), endLocation.getBlockX()) + 1;
                maxX = Math.max(startLocation.getBlockX(), endLocation.getBlockX());
                minY = Math.min(startLocation.getBlockY(), endLocation.getBlockY()) + 1;
                maxY = Math.max(startLocation.getBlockY(), endLocation.getBlockY());
                minZ = Math.min(startLocation.getBlockZ(), endLocation.getBlockZ()) + 1;
                maxZ = Math.max(startLocation.getBlockZ(), endLocation.getBlockZ());
            }
            case INCLUSIVE -> {
                minX = Math.min(startLocation.getBlockX(), endLocation.getBlockX());
                maxX = Math.max(startLocation.getBlockX(), endLocation.getBlockX()) + 1;
                minY = Math.min(startLocation.getBlockY(), endLocation.getBlockY());
                maxY = Math.max(startLocation.getBlockY(), endLocation.getBlockY()) + 1;
                minZ = Math.min(startLocation.getBlockZ(), endLocation.getBlockZ());
                maxZ = Math.max(startLocation.getBlockZ(), endLocation.getBlockZ()) + 1;
            }
        }

        // All 8 corners
        Location frontBottomLeft = new Location(world, minX, minY, minZ); // Vorne unten links
        Location frontBottomRight = new Location(world, maxX, minY, minZ); // Vorne unten rechts
        Location frontTopLeft = new Location(world, minX, maxY, minZ); // Vorne oben links
        Location frontTopRight = new Location(world, maxX, maxY, minZ); // Vorne oben rechts
        Location backBottomLeft = new Location(world, minX, minY, maxZ); // Hinten unten links
        Location backBottomRight = new Location(world, maxX, minY, maxZ); // Hinten unten rechts
        Location backTopLeft = new Location(world, minX, maxY, maxZ); // Hinten oben links
        Location backTopRight = new Location(world, maxX, maxY, maxZ); // Hinten oben rechts

        var markers = List.of(
                // Frontside
                lineProvider.provide(frontBottomLeft, frontBottomRight),
                lineProvider.provide(frontBottomRight, frontTopRight),
                lineProvider.provide(frontTopRight, frontTopLeft),
                lineProvider.provide(frontTopLeft, frontBottomLeft),

                // Backside
                lineProvider.provide(backBottomLeft, backBottomRight),
                lineProvider.provide(backBottomRight, backTopRight),
                lineProvider.provide(backTopRight, backTopLeft),
                lineProvider.provide(backTopLeft, backBottomLeft),

                // Lines between front and back side
                lineProvider.provide(frontBottomLeft, backBottomLeft),
                lineProvider.provide(frontBottomRight, backBottomRight),
                lineProvider.provide(frontTopLeft, backTopLeft),
                lineProvider.provide(frontTopRight, backTopRight)
        );

        markers.forEach(marker -> {
            marker.color(color);
            marker.globallyVisible(globallyVisible);
            marker.glowing(glowing);

            marker.draw();
        });

        dependencyMarkers.addAll(markers);
        return this;
    }

    public enum Alignment {
        INCLUSIVE, EXCLUSIVE, EXACT;
    }

    public void alignment(Alignment alignment) {
        this.alignment = alignment;
    }
}
