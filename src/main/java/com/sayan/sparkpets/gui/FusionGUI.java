package com.sayan.sparkpets.gui;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FusionGUI {

    private final SparkPets plugin;
    private final Player player;

    public FusionGUI(SparkPets plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 27, "§5Pet Fusion");

        inv.setItem(4, new ItemBuilder(Material.ANVIL)
                .name("§e§lPet Fusion Rules")
                .lore(
                        "§7• 5 Regular → Gold §a(100%)",
                        "§7• 4 Regular → Gold §e(80%)",
                        "§7• 4 Gold → Rainbow §a(100%)",
                        "§7• 3 Gold → Rainbow §e(70%)",
                        "§7• 3 Rainbow → Shiny §a(100%)",
                        "§7• 2 Rainbow → Shiny §e(60%)",
                        "",
                        "§cFail = pets lost forever!"
                ).build());

        inv.setItem(11, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Slot 1").build());
        inv.setItem(12, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Slot 2").build());
        inv.setItem(13, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Slot 3").build());
        inv.setItem(14, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Slot 4").build());
        inv.setItem(15, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Slot 5").build());

        inv.setItem(22, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .name("§a§lCLICK TO FUSE")
                .lore("§7Put same rarity pets above")
                .build());

        ItemStack glass = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build();
        for (int i = 0; i < 27; i++) {
            if (inv.getItem(i) == null) inv.setItem(i, glass);
        }

        player.openInventory(inv);
    }
}
