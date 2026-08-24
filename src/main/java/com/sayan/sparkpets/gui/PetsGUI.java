package com.sayan.sparkpets.gui;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.managers.PlayerDataManager;
import com.sayan.sparkpets.models.PetType;
import com.sayan.sparkpets.models.Rarity;
import com.sayan.sparkpets.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetsGUI {

    private final SparkPets plugin;
    private final Player player;
    private final int page; // 0, 1, 2

    public PetsGUI(SparkPets plugin, Player player) {
        this(plugin, player, 0);
    }

    public PetsGUI(SparkPets plugin, Player player, int page) {
        this.plugin = plugin;
        this.player = player;
        this.page = Math.max(0, Math.min(2, page));
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("(" + (page + 1) + "/3) Pets")
                .color(NamedTextColor.DARK_GRAY));

        // Top decorative + buttons
        inv.setItem(4, new ItemBuilder(Material.LIGHT_BLUE_DYE).name("§b§lPETS INFO").lore(
                "§7Pets are companions that grant special bonuses.",
                "§7Each pet provides a unique ability.",
                "§7Fuse matching pets to upgrade their rarity.",
                "§7Higher rarity pets give better boosts.",
                "§7Win pet eggs from envoys, crates, and the black market."
        ).build());

        // Pet Egg button
        inv.setItem(3, new ItemBuilder(Material.ALLAY_SPAWN_EGG)
                .name("§b§lPet Eggs")
                .lore("§7Click to view Pet Eggs")
                .build());

        // Anvil (Fusion)
        inv.setItem(5, new ItemBuilder(Material.ANVIL)
                .name("§e§lPet Fuser")
                .lore(
                        "§7Merge pets together to evolve",
                        "§7them into the next tier!",
                        "",
                        "§eClick to open Fusion GUI!"
                ).build());

        // Active pets slots (middle top-ish)
        PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);

        if (data.hasPrimary()) {
            ItemStack primary = plugin.getPetManager().createPetItem(data.primaryType, data.primaryRarity);
            inv.setItem(13, primary);
        } else {
            inv.setItem(13, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Primary Slot").build());
        }

        if (data.hasSecondary()) {
            ItemStack secondary = plugin.getPetManager().createPetItem(data.secondaryType, data.secondaryRarity);
            inv.setItem(14, secondary);
        } else {
            inv.setItem(14, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Secondary Slot").build());
        }

        // Fill with glass
        ItemStack glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
        for (int i = 0; i < 54; i++) {
            if (inv.getItem(i) == null) inv.setItem(i, glass);
        }

        // Page navigation (simple for now)
        if (page > 0) {
            inv.setItem(45, new ItemBuilder(Material.ARROW).name("§aPrevious Page").build());
        }
        if (page < 2) {
            inv.setItem(53, new ItemBuilder(Material.ARROW).name("§aNext Page").build());
        }

        // Here you will later place the actual pet items in a nice layout
        // For now this is the working skeleton

        player.openInventory(inv);
    }
}
