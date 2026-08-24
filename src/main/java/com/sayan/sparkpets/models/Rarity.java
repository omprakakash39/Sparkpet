package com.sayan.sparkpets.models;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

public enum Rarity {

    REGULAR("Regular", NamedTextColor.WHITE, ChatColor.WHITE, 1.0),
    GOLD("Gold", NamedTextColor.GOLD, ChatColor.GOLD, 1.25),
    RAINBOW("Rainbow", TextColor.color(0xFF55FF), ChatColor.LIGHT_PURPLE, 1.5),
    SHINY("Shiny", NamedTextColor.AQUA, ChatColor.AQUA, 1.75);

    private final String displayName;
    private final TextColor adventureColor;
    private final ChatColor legacyColor;
    private final double multiplier;

    Rarity(String displayName, TextColor adventureColor, ChatColor legacyColor, double multiplier) {
        this.displayName = displayName;
        this.adventureColor = adventureColor;
        this.legacyColor = legacyColor;
        this.multiplier = multiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TextColor getAdventureColor() {
        return adventureColor;
    }

    public ChatColor getLegacyColor() {
        return legacyColor;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getBoldColoredName() {
        return legacyColor.toString() + ChatColor.BOLD + displayName;
    }
}
