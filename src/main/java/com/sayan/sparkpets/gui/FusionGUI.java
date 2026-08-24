package com.sayan.sparkpets.gui;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FusionGUI {

    private final SparkPets plugin;
    private final Player player;

    public FusionGUI(SparkPets plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("Pet Fusion")
                .color(NamedTextColor.DARK_PURPLE));

        // Info item
        inv.setItem(4, new ItemBuilder(Material.ANVIL)
                .name("§e§lPet Fusion Rules")
                .lore(
                        "§7• 5 same Regular → Gold §a(100%)",
                        "§7• 4 same Regular → Gold §e(80%)",
                        "§7• 4 same Gold → Rainbow §a(100%)",
                        "§7• 3 same Gold → Rainbow §e(70%)",
                        "§7• 3 same Rainbow → Shiny §a(100%)",
                        "§7• 2 same Rainbow → Shiny §e(60%)",
                        "",
                        "§cIf fusion fails, all pets are lost forever!"
                ).build());

        // Input slots (example positions)
        inv.setItem(11, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Put pets here").build());
        inv.setItem(12, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Put pets here").build());
        inv.setItem(13, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Put pets here").build());
        inv.setItem(14, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Put pets here").build());
        inv.setItem(15, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("§7Put pets here").build());

        // Fuse button
        inv.setItem(22, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .name("§a§lCLICK TO FUSE")
                .lore("§7Make sure you put correct amount of same pets!")
                .build());

        // Fill rest
        ItemStack glass = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build();
        for (int i = 0; i < 27; i++) {
            if (inv.getItem(i) == null) inv.setItem(i, glass);
        }

        player.openInventory(inv);
    }
}
