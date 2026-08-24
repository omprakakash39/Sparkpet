package com.sayan.sparkpets.gui;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.managers.PlayerDataManager;
import com.sayan.sparkpets.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetsGUI {

    private final SparkPets plugin;
    private final Player player;

    public PetsGUI(SparkPets plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 54, "§8(1/3) Pets");

        // Info
        inv.setItem(4, new ItemBuilder(Material.LIGHT_BLUE_DYE)
                .name("§b§lPETS")
                .lore(
                        "§7Pets are companions that grant special bonuses.",
                        "§7Each pet provides a unique ability.",
                        "§7Fuse matching pets to upgrade their rarity.",
                        "§7Higher rarity pets give better boosts."
                ).build());

        // Egg button
        inv.setItem(3, new ItemBuilder(Material.ALLAY_SPAWN_EGG)
                .name("§b§lPet Eggs")
                .lore("§7Click to view Pet Eggs")
                .build());

        // Fusion button
        inv.setItem(5, new ItemBuilder(Material.ANVIL)
                .name("§e§lPet Fuser")
                .lore("§7Merge pets together to evolve them!", "§eClick to open!")
                .build());

        // Active pets
        PlayerDataManager.ActiveData data = plugin.getPlayerDataManager().get(player);

        if (data.hasPrimary()) {
            inv.setItem(13, plugin.getPetManager().createPetItem(data.primaryType, data.primaryRarity));
        } else {
            inv.setItem(13, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Primary Slot").build());
        }

        if (data.hasSecondary()) {
            inv.setItem(14, plugin.getPetManager().createPetItem(data.secondaryType, data.secondaryRarity));
        } else {
            inv.setItem(14, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Secondary Slot").build());
        }

        // Fill empty slots
        ItemStack glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
        for (int i = 0; i < 54; i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, glass);
            }
        }

        player.openInventory(inv);
    }
}
