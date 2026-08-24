package com.sayan.sparkpets.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public enum PetType {

    // ==================== PRIMARY ====================
    WOLF("Wolf Pet", true, Material.WOLF_SPAWN_EGG,
            "Attack Boost",
            "Increases your attack damage by %value%%!",
            new double[]{8.0, 10.0, 12.0, 14.0}),

    GOLEM("Golem Pet", true, Material.IRON_GOLEM_SPAWN_EGG,
            "Damage Reduction",
            "Decreases your damage taken by %value%%!",
            new double[]{4.0, 6.0, 8.0, 10.0}),

    WITCH("Witch Pet", true, Material.WITCH_SPAWN_EGG,
            "Alchemist",
            "Potion effects last %value%% longer when consumed",
            new double[]{10.0, 15.0, 20.0, 25.0}),

    // ==================== SECONDARY ====================
    VILLAGER("Villager Pet", false, Material.VILLAGER_SPAWN_EGG,
            "Trade Master",
            "Activates Hero of the Village %value%",
            new double[]{2, 3, 4, 5}),

    SILVERFISH("Silverfish Pet", false, Material.SILVERFISH_SPAWN_EGG,
            "Rich Veins",
            "Mining ores gives you a chance for 2x drops.",
            new double[]{5.0, 8.0, 12.0, 15.0}),

    SKELETON("Skeleton Pet", false, Material.SKELETON_SPAWN_EGG,
            "Sharpshooter",
            "Increases projectile damage by %value%%",
            new double[]{10.0, 14.0, 18.0, 22.0}),

    CREEPER("Creeper Pet", false, Material.CREEPER_SPAWN_EGG,
            "Blast Proof",
            "Take %value%% less damage from explosions",
            new double[]{5.0, 8.0, 12.0, 15.0}),

    BANKER("Banker Pet", false, Material.EMERALD,
            "Golden Touch",
            "Increases Sell Boost by %value%%",
            new double[]{5.0, 8.0, 12.0, 15.0}),

    ENDERMAN("Enderman Pet", false, Material.ENDERMAN_SPAWN_EGG,
            "Phase Shift",
            "Gain a %value%% chance to avoid projectile damage",
            new double[]{10.0, 15.0, 20.0, 25.0}),

    WITHER_SKELETON("Wither Skeleton Pet", false, Material.WITHER_SKELETON_SPAWN_EGG,
            "Withering Strike",
            "Attacks have a %value%% chance to apply Wither",
            new double[]{1.0, 2.0, 3.0, 4.0}),

    PIG("Pig Pet", false, Material.PIG_SPAWN_EGG,
            "Pork Power",
            "Getting hit has a %value%% chance to give Regeneration",
            new double[]{0.5, 1.0, 1.5, 2.0}),

    TOTEM("Totem Pet", false, Material.TOTEM_OF_UNDYING,
            "Second Chance",
            "%value%% chance to save you from death",
            new double[]{10.0, 15.0, 20.0, 25.0});

    private final String displayName;
    private final boolean primary;
    private final Material icon;
    private final String abilityName;
    private final String abilityDescription;
    private final double[] values; // index 0=Regular, 1=Gold, 2=Rainbow, 3=Shiny

    PetType(String displayName, boolean primary, Material icon,
            String abilityName, String abilityDescription, double[] values) {
        this.displayName = displayName;
        this.primary = primary;
        this.icon = icon;
        this.abilityName = abilityName;
        this.abilityDescription = abilityDescription;
        this.values = values;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPrimary() {
        return primary;
    }

    public Material getIcon() {
        return icon;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public String getAbilityDescription(Rarity rarity) {
        double value = values[rarity.ordinal()];
        // Special handling for Villager (level instead of %)
        if (this == VILLAGER) {
            return abilityDescription.replace("%value%", String.valueOf((int) value));
        }
        return abilityDescription.replace("%value%", String.format("%.2f", value));
    }

    public double getValue(Rarity rarity) {
        return values[rarity.ordinal()];
    }

    public static List<PetType> getPrimaryPets() {
        return Arrays.stream(values()).filter(PetType::isPrimary).toList();
    }

    public static List<PetType> getSecondaryPets() {
        return Arrays.stream(values()).filter(p -> !p.isPrimary()).toList();
    }
}
