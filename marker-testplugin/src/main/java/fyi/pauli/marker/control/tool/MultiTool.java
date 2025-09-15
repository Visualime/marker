package fyi.pauli.marker.control.tool;

import fyi.pauli.marker.util.ItemBuilder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class MultiTool {
    private int hue = 0;
    private final float saturation = 1.0f;
    private final float lightness = 0.5f;
    private boolean useBlockDisplays = false;
    private Color color = Color.getHSBColor(hue / 360f, saturation, lightness);
    private final ItemStack itemStack = ItemBuilder.of(Material.BRUSH)
            .name(MiniMessage.miniMessage().deserialize("<!i><light_purple>MultiTool"))
            .item();

    public void handleColorPick(boolean rightScroll) {
        hue += rightScroll ? 10 : -10;
        color = Color.getHSBColor(hue / 360f, saturation, lightness);
    }

    public void handleUseBlockDisplays() {
        useBlockDisplays = !useBlockDisplays;
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    public org.bukkit.Color bukkitColor() {
        return org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color color() {
        return color;
    }

    public boolean useBlockDisplays() {
        return useBlockDisplays;
    }
}
