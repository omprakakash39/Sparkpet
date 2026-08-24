package com.sayan.sparkpets.gui;

import com.sayan.sparkpets.SparkPets;
import com.sayan.sparkpets.models.Rarity;
import com.sayan.sparkpets.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EggGUI {

    private final SparkPets plugin;
    private final Player player;

    public EggGUI(SparkPets plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 27, "§bPet Eggs");

        inv.setItem(10, plugin.getPetManager().createPetEgg(Rarity.REGULAR));
        inv.setItem(12, plugin.getPetManager().createPetEgg(Rarity.GOLD));
        inv.setItem(14, plugin.getPetManager().createPetEgg(Rarity.RAINBOW));
        inv.setItem(16, plugin.getPetManager().createPetEgg(Rarity.SHINY));

        inv.setItem(4, new ItemBuilder(Material.BOOK)
                .name("§b§lPet Eggs")
                .lore("§7Only admins can take these.", "§7Right-click egg in inventory to open.")
                .build());

        ItemStack glass = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).name(" ").build();
        for (int i = 0; i < 27; i++) {
            if (inv.getItem(i) == null) inv.setItem(i, glass);
        }

        player.openInventory(inv);
    }
}
