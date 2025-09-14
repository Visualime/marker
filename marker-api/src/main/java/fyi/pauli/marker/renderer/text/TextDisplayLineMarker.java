package fyi.pauli.marker.renderer.text;

import fyi.pauli.marker.renderer.common.LineMarker;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TextDisplayLineMarker extends LineMarker<TextDisplayLineMarker> {
    private static final int LENGTH_STRETCH_FACTOR = 8;
    private static final Component TEXT_DISPLAY_CONTENT = Component.text(" ");

    public TextDisplayLineMarker(Location startLocation, Location endLocation) {
        super(startLocation, endLocation);
    }

    public void draw() {
        for (int i = 0; i < 4; i++) {
            var angle = 90 * i;
            var thickness = (float) 0.3;

            World world = startLocation.getWorld();

            Vector lineDirection = endLocation.toVector().subtract(startLocation.toVector());

            double length = lineDirection.length() * LENGTH_STRETCH_FACTOR;

            Vector center = startLocation.toVector().add(lineDirection.multiply(0.5));
            Location centerLocation = center.toLocation(world);

            Vector normalizedDirection = lineDirection.clone().normalize();

            float yaw = (float) Math.toDegrees(Math.atan2(-normalizedDirection.getX(), normalizedDirection.getZ()));
            float pitch = (float) Math.toDegrees(Math.asin(normalizedDirection.getY()));

            TextDisplay display = world.spawn(centerLocation, TextDisplay.class, textDisplay -> {
                textDisplay.text(TEXT_DISPLAY_CONTENT);
                textDisplay.setBackgroundColor(color);
                textDisplay.setBillboard(Display.Billboard.FIXED);
                textDisplay.setSeeThrough(true); // optional: auch durch Blöcke sichtbar
                textDisplay.setRotation(yaw, -pitch);
                textDisplay.setBrightness(new Display.Brightness(15, 15));
                textDisplay.setGravity(true);
                textDisplay.setPersistent(false);
                textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
                textDisplay.setViewRange(1000);
            });

            var lineLength = length + thickness * 2 - 0.1;

            Transformation transformation = display.getTransformation();
            transformation.getScale().set(lineLength, thickness, thickness);

            /* We rotate the display so the text direction is the same as the eye direction of the display.
             Also, since we summon 4 displays for one line to make if visible from all angled, we need to rotate each line
             by 90 degrees compared to the previous one
             */
            var rotation = new Quaternionf().rotateY((float) Math.toRadians(90)).rotateLocalZ((float) Math.toRadians(angle));
            transformation.getLeftRotation().set(rotation);

            // We need to offset the line in line direction since the center of a text display is not the exact center of the text
            transformation.getTranslation().set(0, 0, 0.0125 * length);

            // We need to offset the line so the center of the display in y direction is the actual middle of the text.
            Vector3f offset = transformation.getLeftRotation().transform(new Vector3f(0, -0.12f * thickness, 0), new Vector3f());
            transformation.getTranslation().add(offset);

            display.setTransformation(transformation);
            markerEntities.add(display);
        }
    }
}