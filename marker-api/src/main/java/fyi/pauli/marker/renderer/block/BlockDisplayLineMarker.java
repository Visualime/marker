package fyi.pauli.marker.renderer.block;

import fyi.pauli.marker.renderer.common.LineMarker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

public class BlockDisplayLineMarker extends LineMarker<BlockDisplayLineMarker> {
    public BlockDisplayLineMarker(Location startLocation, Location endLocation) {
        super(startLocation, endLocation);
    }

    @Override
    public BlockDisplayLineMarker draw() {
        var thickness = (float) 0.05;

        World world = startLocation.getWorld();

        Vector lineDirection = endLocation.toVector().subtract(startLocation.toVector());

        double length = lineDirection.length();

        Vector center = startLocation.toVector().add(lineDirection.multiply(0.5));
        Location centerLocation = center.toLocation(world);

        Vector normalizedDirection = lineDirection.clone().normalize();

        float yaw = (float) Math.toDegrees(Math.atan2(-normalizedDirection.getX(), normalizedDirection.getZ()));
        float pitch = (float) Math.toDegrees(Math.asin(normalizedDirection.getY()));

        BlockDisplay display = world.spawn(centerLocation, BlockDisplay.class, blockDisplay -> {
            blockDisplay.setBillboard(Display.Billboard.FIXED);
            blockDisplay.setRotation(yaw, -pitch);
            blockDisplay.setBrightness(new Display.Brightness(15, 15));
            blockDisplay.setGravity(true);
            blockDisplay.setPersistent(false);
            blockDisplay.setViewRange(1000);
            blockDisplay.setBlock(Material.RED_STAINED_GLASS.createBlockData());
            blockDisplay.setGlowing(glowing);
            blockDisplay.setGlowColorOverride(color);
        });

        Transformation transformation = display.getTransformation();
        transformation.getScale().set(thickness, thickness, length);
        transformation.getTranslation().set(-thickness / 2, -thickness / 2, -length / 2);

        display.setTransformation(transformation);
        markerEntities.add(display);
        return this;
    }
}
