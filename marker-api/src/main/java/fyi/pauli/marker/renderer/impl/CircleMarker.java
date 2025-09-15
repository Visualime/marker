package fyi.pauli.marker.renderer.impl;

import fyi.pauli.marker.provider.BlockDisplayLineMarkerProvider;
import fyi.pauli.marker.provider.LineMarkerProvider;
import fyi.pauli.marker.provider.TextDisplayLineMarkerProvider;
import fyi.pauli.marker.renderer.MultiLineMarker;
import org.bukkit.Location;

import java.util.ArrayList;

public class CircleMarker extends MultiLineMarker<CircleMarker> {
    protected static final int DEFAULT_SEGMENTS = 16;
    protected final Location center;
    protected final double radius;
    protected Orientation orientation = Orientation.HORIZONTAL;

    protected CircleMarker(LineMarkerProvider lineProvider, Location center, double radius) {
        super(lineProvider);
        this.center = center;
        this.radius = radius;
    }

    public static CircleMarker text(Location center, double radius) {
        return new CircleMarker(TextDisplayLineMarkerProvider.INSTANCE, center, radius);
    }

    public static CircleMarker block(Location center, double radius) {
        return new CircleMarker(BlockDisplayLineMarkerProvider.INSTANCE, center, radius);
    }

    @Override
    public CircleMarker draw() {
        var segments = DEFAULT_SEGMENTS;
        var circlePoints = new ArrayList<Location>();

        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;

            double x, y, z;
            if (orientation == Orientation.HORIZONTAL) {
                x = center.getX() + radius * Math.cos(angle);
                y = center.getY();
                z = center.getZ() + radius * Math.sin(angle);
            } else {
                x = center.getX() + radius * Math.cos(angle);
                y = center.getY() + radius * Math.sin(angle);
                z = center.getZ() + radius * Math.cos(angle);
            }

            circlePoints.add(new Location(center.getWorld(), x, y, z));
        }

        for (int i = 0; i < circlePoints.size(); i++) {
            Location current = circlePoints.get(i);
            Location next = circlePoints.get((i + 1) % circlePoints.size());

            if (current.distance(next) > 0.01) {
                var marker = lineProvider.provide(current, next).inheritProps(this).draw();

                dependencyMarkers.add(marker);
            }
        }
        return this;
    }
    public enum Orientation {
        HORIZONTAL, VERTICAL;
    }

    public void orientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
