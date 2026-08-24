package com.sayan.sparkpets.managers;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.models.PetType;
import com.sayan.sparkpets.models.Rarity;
import com.sayan.sparkpets.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public class PetManager {

    private final SparkPets plugin;
    private final NamespacedKey petTypeKey;
    private final NamespacedKey rarityKey;
    private final NamespacedKey isPetKey;
    private final NamespacedKey isEggKey;

    public PetManager(SparkPets plugin) {
        this.plugin = plugin;
        this.petTypeKey = new NamespacedKey(plugin, "pet_type");
        this.rarityKey = new NamespacedKey(plugin, "pet_rarity");
        this.isPetKey = new NamespacedKey(plugin, "is_pet");
        this.isEggKey = new NamespacedKey(plugin, "is_pet_egg");
    }

    public ItemStack createPetItem(PetType type, Rarity rarity) {
        ItemBuilder builder = new ItemBuilder(type.getIcon());

        // Name
        String nameColor = switch (rarity) {
            case REGULAR -> "§f§l";
            case GOLD -> "§6§l";
            case RAINBOW -> "§d§l";
            case SHINY -> "§b§l";
        };
        builder.name(nameColor + type.getDisplayName());

        // Lore
        List<String> lore = new ArrayList<>();
        lore.add("§8| §7Rarity: " + rarity.getBoldColoredName());
        lore.add("§8| §7Type: " + (type.isPrimary() ? "§aPrimary" : "§6Secondary"));
        lore.add("");
        lore.add("§c" + type.getAbilityName());
        lore.add("§7" + type.getAbilityDescription(rarity));
        lore.add("");
        lore.add("§eRight-click to activate!");

        builder.lore(lore.toArray(new String[0]));
        builder.hideFlags();

        ItemStack item = builder.build();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(isPetKey, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(petTypeKey, PersistentDataType.STRING, type.name());
        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity.name());
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createPetEgg(Rarity rarity) {
        ItemBuilder builder = new ItemBuilder(Material.ALLAY_SPAWN_EGG);

        String name = switch (rarity) {
            case REGULAR -> "§f§lRegular Pet Egg";
            case GOLD -> "§6§lGold Pet Egg";
            case RAINBOW -> "§d§lRainbow Pet Egg";
            case SHINY -> "§b§lShiny Pet Egg";
        };
        builder.name(name);

        builder.lore(
                "§7Right-click to open!",
                "§7Receives a random " + rarity.getBoldColoredName() + " §7pet.",
                "",
                "§eClick to open!"
        );
        builder.hideFlags();

        ItemStack item = builder.build();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(isEggKey, PersistentDataType.BYTE, (byte) 1);
        meta.getPersistentDataContainer().set(rarityKey, PersistentDataType.STRING, rarity.name());
        item.setItemMeta(meta);

        return item;
    }

    public boolean isPet(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(isPetKey, PersistentDataType.BYTE);
    }

    public boolean isPetEgg(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(isEggKey, PersistentDataType.BYTE);
    }

    public PetType getPetType(ItemStack item) {
        if (!isPet(item)) return null;
        String name = item.getItemMeta().getPersistentDataContainer().get(petTypeKey, PersistentDataType.STRING);
        try {
            return PetType.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    public Rarity getRarity(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String name = item.getItemMeta().getPersistentDataContainer().get(rarityKey, PersistentDataType.STRING);
        try {
            return Rarity.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    public NamespacedKey getPetTypeKey() {
        return petTypeKey;
    }

    public NamespacedKey getRarityKey() {
        return rarityKey;
    }
}
