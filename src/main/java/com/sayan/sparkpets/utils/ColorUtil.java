package com.sayan.sparkpets.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;

public class ColorUtil {

    private static final MiniMessage MINI = MiniMessage.miniMessage();

    public static Component color(String text) {
        return MINI.deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static String legacy(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Component bold(String text, NamedTextColor color) {
        return Component.text(text).color(color).decorate(TextDecoration.BOLD)
                .decoration(TextDecoration.ITALIC, false);
    }
}
