package fyi.pauli.marker.renderer.text;

import fyi.pauli.marker.renderer.common.LineMarker;
import fyi.pauli.marker.renderer.common.SphereMarker;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class TextDisplaySphereMarker extends SphereMarker<TextDisplaySphereMarker> {
    public TextDisplaySphereMarker(Location center, double radius) {
        super(center, radius);
    }

    @Override
    public TextDisplaySphereMarker draw() {
        List<Location> points = new ArrayList<>();
        var resolution = 16;

        // Find the North Pole
        Location northPole = new Location(center.getWorld(),
                center.getX(), center.getY() + radius, center.getZ());
        points.add(northPole);

        // Find middle rings
        for (int i = 1; i < resolution; i++) {
            double phi = Math.PI * i / resolution;

            for (int j = 0; j < resolution * 2; j++) {
                double theta = 2 * Math.PI * j / (resolution * 2);

                double x = center.getX() + radius * Math.sin(phi) * Math.cos(theta);
                double y = center.getY() + radius * Math.cos(phi);
                double z = center.getZ() + radius * Math.sin(phi) * Math.sin(theta);

                points.add(new Location(center.getWorld(), x, y, z));
            }
        }

        // Find the South Pole
        Location southPole = new Location(center.getWorld(),
                center.getX(), center.getY() - radius, center.getZ());
        points.add(southPole);

        connectSphereWithPoles(points, color, resolution);
        return this;
    }

    private void connectSphereWithPoles(List<Location> points, Color color, int resolution) {
        int pointsPerRing = resolution * 2;
        Location northPole = points.getFirst();
        Location southPole = points.getLast();

        // Connect the North Pole with the first ring
        for (int i = 0; i < pointsPerRing; i++) {
            var marker = LineMarker.text(northPole, points.get(i + 1)).inheritProps(this).draw();
            dependencyMarkers.add(marker);
        }

        // Connect the middle rings
        for (int ring = 0; ring < resolution - 1; ring++) {
            for (int i = 0; i < pointsPerRing; i++) {
                int currentIndex = 1 + ring * pointsPerRing + i;
                int nextIndex = 1 + ring * pointsPerRing + ((i + 1) % pointsPerRing);

                var marker = LineMarker.text(points.get(currentIndex), points.get(nextIndex))
                        .inheritProps(this)
                        .draw();

                dependencyMarkers.add(marker);

                // Form the middle rings
                if (ring < resolution - 2) {
                    int nextRingIndex = 1 + (ring + 1) * pointsPerRing + i;

                    var markerRing = LineMarker.text(points.get(currentIndex), points.get(nextRingIndex))
                            .inheritProps(this)
                            .draw();

                    dependencyMarkers.add(markerRing);
                }
            }
        }

        // Connect the South Pole with the last ring
        int lastRingStart = points.size() - 1 - pointsPerRing;
        for (int i = 0; i < pointsPerRing; i++) {
            var marker = LineMarker.text(points.get(lastRingStart + i), southPole)
                    .inheritProps(this)
                    .draw();

            dependencyMarkers.add(marker);
        }
    }
}
