package fyi.pauli.marker.renderer.text;

import fyi.pauli.marker.renderer.common.CircleMarker;
import fyi.pauli.marker.renderer.common.LineMarker;
import org.bukkit.Location;

import java.util.ArrayList;

public class TextDisplayCircleMarker extends CircleMarker<TextDisplayCircleMarker> {
    public TextDisplayCircleMarker(Location center, double radius) {
        super(center, radius);
    }

    @Override
    public void draw() {
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
                var marker = LineMarker.text(current, next).configure(textDisplayLineMarker -> {
                    textDisplayLineMarker.color(color);
                    textDisplayLineMarker.globallyVisible(globallyVisible);
                    textDisplayLineMarker.glowing(glowing);
                });

                marker.draw();

                edges.add(marker);
            }
        }
    }
}
